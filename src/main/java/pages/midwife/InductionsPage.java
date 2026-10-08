package pages.midwife;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import drivers.DriverManager;

@Slf4j
public class InductionsPage extends BasePage {

    // Precise locator (XPath-only) for the main Inductions dashboard patient rows
    private By inductionsPatientRowsBy = By.xpath("//app-inductions-wrapper//table[contains(@class,'p-datatable-table')]//tbody//tr");

    // Locator (XPath-only, relative) for the "זירוזים קודמים" link/button within a patient row
    private By previousInductionsButtonRel = By.xpath(".//*[contains(@class,'previous-inductions-link')]");

    public InductionsPage() {
      // UIActions.waitForSpinnerToDisappear();
    }

    /**
     * Save the full page HTML source to `test-output/ui-capture/{fileName}.html` for investigator.
     */
    public Path savePageDomSnapshot(String fileName) throws IOException {
        String pageSource = DriverManager.getInstance().getPageSource();
        Path outDir = Path.of("test-output", "ui-capture");
        Files.createDirectories(outDir);
        Path outFile = outDir.resolve(fileName + ".html");
        Files.writeString(outFile, pageSource, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        log.info("Saved page DOM snapshot to {}", outFile.toString());
        return outFile;
    }

    /**
     * Capture a full-page screenshot and save to `test-output/ui-capture/{fileName}.png`.
     */
    public Path saveScreenshot(String fileName) throws IOException {
        File src = ((TakesScreenshot) DriverManager.getInstance()).getScreenshotAs(OutputType.FILE);
        Path outDir = Path.of("test-output", "ui-capture");
        Files.createDirectories(outDir);
        Path outFile = outDir.resolve(fileName + ".png");
        Files.copy(src.toPath(), outFile);
        log.info("Saved screenshot to {}", outFile.toString());
        return outFile;
    }


    /**
     * Open the previous inductions popup for a patient and return true if popup opened
     */
    public boolean openPreviousInductionsPopup(String sectionTitle, String patientDisplayName) {
        WebElement row = findPatientRowInSection(sectionTitle, patientDisplayName);
        if (row == null) {
            log.warn("Patient row not found in section {}: {}", sectionTitle, patientDisplayName);
            return false;
        }
        WebElement btn = row.findElement(previousInductionsButtonRel);
        UIActions.click(btn);
        UIActions.waitForSpinnerToDisappear();
        return true;
    }

    /**
     * Return a section container WebElement by the displayed section title (e.g., "ממתינות לזירוז", "זירוזים פעילים").
     */
    public WebElement findSectionByTitle(String sectionTitle) {
        String xpath = "//app-inductions-wrapper//section[contains(@class,'department-section')][.//h3[normalize-space(text())='" + sectionTitle + "']]";
        By by = By.xpath(xpath);
        return UIActions.findElementWithWait(by);
    }

    /**
     * Find a patient row inside a specific section by its display name.
     */
  public WebElement findPatientRowInSection(String sectionTitle, String patientDisplayName) {
    UIActions.waitForSpinnerToDisappear();
    WebElement section = findSectionByTitle(sectionTitle);
    if (section == null) return null;
    List<WebElement> rows = section.findElements(By.xpath(".//table[contains(@class,'p-datatable-table')]//tbody//tr"));
    String[] nameParts = patientDisplayName.split("[_\\s]+");

    for (WebElement row : rows) {
        // שליפת הטקסט המלא מתוך ה-DOM (פתרון לבעיית getText() ריק)
        String rowText = row.getAttribute("textContent");
        
        if (rowText != null && !rowText.trim().isEmpty()) {
            boolean matchesAllParts = Arrays.stream(nameParts)
                    .allMatch(part -> rowText.contains(part.trim()));
                    
            if (matchesAllParts) {
                return row;
            }
        }
    }
    
    log.warn("Patient '{}' was not found in section '{}'", patientDisplayName, sectionTitle);
    return null;
}

    /**
     * Open previous inductions popup for a patient inside a named section.
     */
    public boolean openPreviousInductionsPopupInSection(String sectionTitle, String patientDisplayName) {
        WebElement row = findPatientRowInSection(sectionTitle, patientDisplayName);
        if (row == null) {
            log.warn("Patient row not found in section {}: {}", sectionTitle, patientDisplayName);
            return false;
        }
        WebElement btn = row.findElement(previousInductionsButtonRel);
        UIActions.click(btn);
        UIActions.waitForSpinnerToDisappear();
        return true;
    }

    /**
     * Click the 'Previous Inductions' control on the very first patient row in the table.
     * Returns true if click action was performed, false otherwise.
     */
    public boolean clickPreviousInductionsOnFirstRow() {
        UIActions.waitForSpinnerToDisappear();
        List<WebElement> rows = UIActions.findElementsWithWait(inductionsPatientRowsBy);
        if (rows == null || rows.isEmpty()) {
            log.warn("No induction patient rows found to click previous-inductions on");
            return false;
        }
        WebElement firstRow = rows.get(0);
        try {
            WebElement btn = firstRow.findElement(previousInductionsButtonRel);
            UIActions.click(btn);
            // Allow the popover to render into the DOM (it's mounted to body dynamically)
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            UIActions.waitForSpinnerToDisappear();
            return true;
        } catch (Exception e) {
            log.warn("Failed to click previous-inductions on first row: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if the previous inductions popup contains the given directive text
     */
    public boolean 
    previousInductionsPopupContains(String text) {
        UIActions.waitForSpinnerToDisappear();
        // Try to find any visible popover elements first (these are typically attached to document body)
        By[] candidateSelectors = new By[] {
                By.cssSelector("ngb-popover-window"),
                By.cssSelector("div.induction-history-popover"),
                By.cssSelector("div.popover")
        };

        long deadline = System.currentTimeMillis() + 3000;
        while (System.currentTimeMillis() < deadline) {
            try {
                // 1) visible popovers
                for (By sel : candidateSelectors) {
                    List<WebElement> pops = DriverManager.getInstance().findElements(sel);
                    for (WebElement p : pops) {
                        try {
                            if (!p.isDisplayed()) continue;
                            String txt = p.getText();
                            if (txt != null && txt.contains(text)) {
                                log.debug("Found text '{}' in visible popover via selector {}", text, sel);
                                return true;
                            }
                        } catch (Exception ignored) {}
                    }
                }

                // 2) fallback: map aria-describedby from any previous-inductions-link element to popover id
                List<WebElement> links = DriverManager.getInstance().findElements(previousInductionsButtonRel);
                for (WebElement link : links) {
                    try {
                        String described = link.getAttribute("aria-describedby");
                        if (described != null && !described.isBlank()) {
                            By byId = By.id(described);
                            List<WebElement> els = DriverManager.getInstance().findElements(byId);
                            for (WebElement el : els) {
                                if (!el.isDisplayed()) continue;
                                String txt = el.getText();
                                if (txt != null && txt.contains(text)) {
                                    log.debug("Found text '{}' in popover by aria-describedby id {}", text, described);
                                    return true;
                                }
                            }
                        }
                    } catch (Exception ignored) {}
                }

            } catch (Exception e) {
                // continue retrying
            }
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
        }

        log.warn("Previous inductions popup did not contain '{}' after retries", text);
        return false;
    }
}
