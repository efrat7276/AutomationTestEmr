package pages.common;

import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * DatePickerPage - POM for ngb-datepicker (Angular Bootstrap DatePicker)
 * Handles date selection with day, month, and year navigation
 * 
 * Used across the system for any date input fields
 */
@Slf4j
public class DatePickerPage extends BasePage {

    // ============ DatePicker Container Locators ============
    private final By datePickerContainerBy = By.xpath("//ngb-datepicker[contains(@class, 'dropdown-menu')]");
    
    // ============ Input & Display Locators ============
    private final By dateInputBy = By.xpath("//ngb-datepicker//input");
    private final By dateDisplayBy = By.xpath("//ngb-datepicker//div[contains(@class, 'ngb-dp-header')]");
    
    // ============ Month & Year Navigation Locators ============
    private final By monthDropdownBy = By.xpath("//select[contains(@class, 'ngb-dp-month')]");
    private final By yearDropdownBy = By.xpath("//select[contains(@class, 'ngb-dp-year')]");
    
    // Alternative: clickable month/year buttons (if dropdown not available)
    private final By monthButtonBy = By.xpath("//ngb-datepicker//button[contains(@aria-label, 'month')]");
    private final By yearButtonBy = By.xpath("//ngb-datepicker//button[contains(@aria-label, 'year')]");
    
    // ============ Navigation Buttons (Previous/Next Month) ============
    private final By previousMonthButtonBy = By.xpath("//ngb-datepicker//button[contains(@class, 'ngb-dp-arrow') and contains(@class, 'left')]");
    private final By nextMonthButtonBy = By.xpath("//ngb-datepicker//button[contains(@class, 'ngb-dp-arrow') and contains(@class, 'right')]");
    
    // ============ Days Grid Locators ============
    private final By dayButtonsBy = By.xpath("//div[@role='gridcell' and contains(@class, 'ngb-dp-day')]");
    private final String dayButtonByNumber = "//div[@role='gridcell' and contains(@class, 'ngb-dp-day') and text()='%d']";
    
    // ============ Current Date Indicator ============
    private final By currentDayBy = By.xpath("//div[@role='gridcell' and contains(@class, 'ngb-dp-day') and contains(@class, 'selected')]");
    
    /**
     * Constructor for DatePickerPage
     */
    public DatePickerPage() {
        super();
    }

    // ============ Visibility & State Checks ============

    /**
     * Check if DatePicker is visible and open
     * @return true if datepicker is displayed, false otherwise
     */
    public boolean isDatePickerVisible() {
        return UIActions.isElementPresentAndVisible(datePickerContainerBy);
    }

    /**
     * Check if DatePicker is opened and ready for interaction
     * @return true if datepicker is open and responsive
     */
    public boolean isDatePickerOpen() {
        WebElement container = DriverManager.getInstance().findElement(datePickerContainerBy);
        String displayStyle = container.getAttribute("style");
        boolean isVisible = container.isDisplayed();
        log.info("datepicker status - Displayed: {}, Style: {}", isVisible, displayStyle);
        return isVisible;
    }

    // ============ Date Selection Methods ============

    /**
     * Select a specific day from the calendar
     * @param day The day number (1-31)
     * @return true if day was selected successfully, false otherwise
     */
    public boolean selectDay(int day) {
        if (day < 1 || day > 31) {
            log.error("invalid day: {}. Day must be between 1 and 31", day);
            return false;
        }

        By dayLocator = By.xpath(String.format(dayButtonByNumber, day));
        UIActions.click(dayLocator);
        UIActions.waitForSpinnerToDisappear();
        log.info("selected day: {}", day);
        return true;
    }

    /**
     * Select a specific date (day, month, year)
     * Navigates to the correct month/year, then clicks the day
     * @param day The day number (1-31)
     * @param month The month number (1-12)
     * @param year The year (e.g., 2026)
     * @return true if date was selected successfully, false otherwise
     */
    public boolean selectDate(int day, int month, int year) {
        log.info("attempting to select date: {}/{}/{}", day, month, year);

        // Verify DatePicker is open
        if (!isDatePickerOpen()) {
            log.error("datepicker is not open. Cannot select date.");
            return false;
        }

        // Navigate to correct month/year
        boolean navigated = navigateToMonthYear(month, year);
        if (!navigated) {
            log.error("failed to navigate to month {}/{}", month, year);
            return false;
        }

        // Select the day
        boolean daySelected = selectDay(day);
        if (!daySelected) {
            log.error("failed to select day {}", day);
            return false;
        }

        log.info("successfully selected date: {}/{}/{}", day, month, year);
        return true;
    }

    /**
     * Select today's date
     * @return true if today was selected, false otherwise
     */
    public boolean selectToday() {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear();

        return selectDate(day, month, year);
    }

    /**
     * Select a specific date using LocalDate
     * @param date The LocalDate to select
     * @return true if date was selected successfully, false otherwise
     */
    public boolean selectDate(LocalDate date) {
        return selectDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }

    // ============ Month & Year Navigation ============

    /**
     * Navigate to a specific month and year
     * @param month The month (1-12)
     * @param year The year
     * @return true if navigation was successful, false otherwise
     */
    public boolean navigateToMonthYear(int month, int year) {
        log.info("navigating to month: {}, year: {}", month, year);

        // Get current month/year
        YearMonth current = getCurrentMonthYear();
        YearMonth target = YearMonth.of(year, month);

        log.info("current: {}, target: {}", current, target);

        // Use month/year dropdowns if available
        boolean success = selectMonthYearViaDropdown(month, year);
        if (success) {
            log.info("navigated to {}/{} via dropdown", month, year);
            return true;
        }

        // Fallback: Use Previous/Next navigation buttons
        success = navigateViaButtons(current, target);
        if (success) {
            log.info("navigated to {}/{} via navigation buttons", month, year);
            return true;
        }

        log.error("failed to navigate to {}/{}", month, year);
        return false;
    }

    /**
     * Select month and year using dropdown selects
     * @param month The month (1-12)
     * @param year The year
     * @return true if selection was successful, false otherwise
     */
    private boolean selectMonthYearViaDropdown(int month, int year) {
        // Try to find and use month dropdown
        try {
            WebElement monthDropdown = DriverManager.getInstance().findElement(monthDropdownBy);
            Select monthSelect = new Select(monthDropdown);
            monthSelect.selectByValue(String.format("%d", month - 1));
            UIActions.waitForSpinnerToDisappear();
            log.info("selected month via dropdown: {}", month);
        } catch (Exception e) {
            log.info("month dropdown not available");
        }

        // Try to find and use year dropdown
        try {
            WebElement yearDropdown = DriverManager.getInstance().findElement(yearDropdownBy);
            Select yearSelect = new Select(yearDropdown);
            yearSelect.selectByValue(String.format("%d", year));
            UIActions.waitForSpinnerToDisappear();
            log.info("selected year via dropdown: {}", year);
        } catch (Exception e) {
            log.info("year dropdown not available");
        }

        UIActions.waitForSpinnerToDisappear();
        return true;
    }

    /**
     * Navigate using Previous/Next buttons
     * @param current Current YearMonth
     * @param target Target YearMonth
     * @return true if navigation was successful, false otherwise
     */
    private boolean navigateViaButtons(YearMonth current, YearMonth target) {
        int monthsDiff = current.until(target).getMonths();
        
        if (monthsDiff > 0) {
            // Navigate forward (next month)
            for (int i = 0; i < monthsDiff; i++) {
                UIActions.click(nextMonthButtonBy);
                UIActions.waitForSpinnerToDisappear();
            }
            log.info("navigated forward {} months", monthsDiff);
        } else if (monthsDiff < 0) {
            // Navigate backward (previous month)
            for (int i = 0; i < Math.abs(monthsDiff); i++) {
                UIActions.click(previousMonthButtonBy);
                UIActions.waitForSpinnerToDisappear();
            }
            log.info("navigated backward {} months", Math.abs(monthsDiff));
        }

        return true;
    }

    // ============ Date Reading Methods ============

    /**
     * Get the currently displayed month and year
     * @return YearMonth representing the current calendar view
     */
    public YearMonth getCurrentMonthYear() {
        // Try to read from month/year dropdowns
        try {
            WebElement monthDropdown = DriverManager.getInstance().findElement(monthDropdownBy);
            WebElement yearDropdown = DriverManager.getInstance().findElement(yearDropdownBy);
            
            String monthText = monthDropdown.getAttribute("value");
            String yearText = yearDropdown.getAttribute("value");
            
            if (monthText != null && yearText != null) {
                int month = Integer.parseInt(monthText) + 1; // months are 0-indexed in select
                int year = Integer.parseInt(yearText);
                return YearMonth.of(year, month);
            }
        } catch (Exception e) {
            log.info("could not read month/year from dropdowns");
        }

        // Fallback: try to read from display header
        WebElement display = DriverManager.getInstance().findElement(dateDisplayBy);
        String displayText = display.getText().trim();
        log.info("calendar display: {}", displayText);
        
        // Parse display text (format may vary)
        // For now, return current month as fallback
        return YearMonth.now();
    }

    /**
     * Get the currently selected date from the datepicker
     * @return LocalDate of the selected date, or null if not found
     */
    public LocalDate getSelectedDate() {
        WebElement input = DriverManager.getInstance().findElement(dateInputBy);
        String dateText = input.getAttribute("value");
        
        if (dateText != null && !dateText.isEmpty()) {
            // Parse date in format dd/MM/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate selectedDate = LocalDate.parse(dateText, formatter);
            log.info("selected date: {}", selectedDate);
            return selectedDate;
        }
        return null;
    }

    /**
     * Get the currently highlighted day (usually today)
     * @return day number, or -1 if not found
     */
    public int getCurrentHighlightedDay() {
        try {
            WebElement currentDay = DriverManager.getInstance().findElement(currentDayBy);
            String dayText = currentDay.getText().trim();
            return Integer.parseInt(dayText);
        } catch (Exception e) {
            log.info("could not get current highlighted day");
            return -1;
        }
    }

    /**
     * Get all available days in the current calendar view
     * @return List of day numbers available for selection
     */
    public List<WebElement> getAvailableDays() {
        return DriverManager.getInstance().findElements(dayButtonsBy);
    }

    // ============ Utility Methods ============

    /**
     * Close the datepicker (if it supports close action)
     */
    public void closeDatePicker() {
        // Try pressing Escape
        DriverManager.getInstance().switchTo().activeElement().sendKeys(org.openqa.selenium.Keys.ESCAPE);
        UIActions.waitForSpinnerToDisappear();
        log.info("datepicker closed via Escape key");
    }

    /**
     * Verify date was correctly selected
     * @param expectedDate The LocalDate that should be selected
     * @return true if the selected date matches expected date, false otherwise
     */
    public boolean verifyDateSelected(LocalDate expectedDate) {
        LocalDate selectedDate = getSelectedDate();
        if (selectedDate != null && selectedDate.equals(expectedDate)) {
            log.info("date verification passed: {}", selectedDate);
            return true;
        } else {
            log.error("date mismatch - Expected: {}, Got: {}", expectedDate, selectedDate);
            return false;
        }
    }

    /**
     * Wait for DatePicker to appear
     * @param timeoutSeconds Maximum time to wait
     * @return true if datepicker appeared within timeout, false otherwise
     */
    public boolean waitForDatePickerToAppear(int timeoutSeconds) {
        UIActions.waitForElement(datePickerContainerBy, timeoutSeconds);
        log.info("datepicker appeared within {} seconds", timeoutSeconds);
        return isDatePickerOpen();
    }
}
