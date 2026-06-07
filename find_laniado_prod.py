"""
Script to find the LaniadoProd element on the Production page
Helps locate the XPath for LaniadoProd in Cardex or Header
"""

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service
import time

# Production URL (from DataConfig.xml)
PROD_URL = "https://emr.laniado.org.il/emr2/#/login"

# Credentials
USERNAME = "test"
PASSWORD = "Te231121"

def setup_driver():
    """Initialize Chrome WebDriver"""
    options = webdriver.ChromeOptions()
    options.add_argument("--start-maximized")
    options.add_argument("--disable-blink-features=AutomationControlled")
    options.add_experimental_option("excludeSwitches", ["enable-automation"])
    options.add_experimental_option("useAutomationExtension", False)
    
    service = Service(ChromeDriverManager().install())
    driver = webdriver.Chrome(service=service, options=options)
    return driver

def login_to_production(driver):
    """Login to Production environment"""
    print(f"🔓 Navigating to: {PROD_URL}")
    driver.get(PROD_URL)
    time.sleep(3)
    
    print(f"📝 Logging in as: {USERNAME}")
    # Find and fill username
    username_field = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.ID, "user_name"))
    )
    username_field.clear()
    username_field.send_keys(USERNAME)
    
    # Find and fill password
    password_field = driver.find_element(By.ID, "password")
    password_field.clear()
    password_field.send_keys(PASSWORD)
    
    # Click submit
    submit_btn = driver.find_element(By.ID, "submitBtn")
    submit_btn.click()
    
    print("⏳ Waiting for login to complete...")
    time.sleep(5)
    
    # Check if role selection page appears
    try:
        role_elements = WebDriverWait(driver, 10).until(
            EC.presence_of_all_elements_located((By.XPATH, "//ul[@class='list-group']/li"))
        )
        print(f"✅ Found {len(role_elements)} roles. Selecting first one (רופא)...")
        role_elements[0].click()
        time.sleep(3)
    except:
        print("⚠️ No role selection page found, continuing...")

def find_laniado_prod_element(driver):
    """Search for LaniadoProd element"""
    print("\n🔍 Searching for LaniadoProd element...")
    
    # Try multiple XPath patterns
    search_patterns = [
        "//span[contains(text(), 'LaniadoProd')]",
        "//div[contains(text(), 'LaniadoProd')]",
        "//p[contains(text(), 'LaniadoProd')]",
        "//h1[contains(text(), 'Laniado')]",
        "//header//span[contains(text(), 'Laniado')]",
        "//app-root//span[contains(text(), 'Laniado')]",
        "//*[contains(text(), 'LaniadoProd')]",
        "//*[contains(text(), 'Production')]",
        "//span[@class='header-title']",
        "//div[@class='header-info']",
    ]
    
    found_elements = []
    
    for pattern in search_patterns:
        try:
            elements = driver.find_elements(By.XPATH, pattern)
            if elements:
                for elem in elements:
                    text = elem.text.strip()
                    if text:
                        found_elements.append({
                            'xpath': pattern,
                            'tag': elem.tag_name,
                            'text': text,
                            'class': elem.get_attribute('class'),
                            'id': elem.get_attribute('id')
                        })
                        print(f"✅ Found: {pattern}")
                        print(f"   - Tag: {elem.tag_name}")
                        print(f"   - Text: {text}")
                        print(f"   - Class: {elem.get_attribute('class')}")
                        print(f"   - ID: {elem.get_attribute('id')}")
                        print()
        except:
            pass
    
    if not found_elements:
        print("❌ LaniadoProd element not found with predefined patterns")
        print("\n🔎 Scanning all page text for 'Laniado' or 'Production'...")
        try:
            page_source = driver.page_source
            if 'LaniadoProd' in page_source:
                print("✅ 'LaniadoProd' found in page source!")
            elif 'Laniado' in page_source:
                print("✅ 'Laniado' found in page source")
            elif 'Production' in page_source:
                print("✅ 'Production' found in page source")
            else:
                print("❌ No Laniado/Production references found")
        except:
            pass
    
    return found_elements

def navigate_to_cardex(driver):
    """Navigate to Cardex page to find LaniadoProd"""
    print("\n📍 Attempting to navigate to Cardex page...")
    
    try:
        # Wait for main menu to load
        time.sleep(2)
        
        # Look for patient selection
        try:
            patient_rows = driver.find_elements(By.XPATH, "//p-table//tr//td[1]")
            if patient_rows:
                print(f"✅ Found {len(patient_rows)} patients")
                patient_rows[0].click()
                print("✅ Selected first patient")
                time.sleep(3)
            else:
                print("⚠️ No patients found")
        except:
            print("⚠️ Could not select patient")
        
        # Look for Cardex button/menu
        try:
            cardex_menu = driver.find_element(By.XPATH, "//*[contains(text(), 'קרדקס')]")
            cardex_menu.click()
            print("✅ Clicked Cardex menu")
            time.sleep(3)
        except:
            print("⚠️ Could not find Cardex menu")
    
    except Exception as e:
        print(f"❌ Error navigating to Cardex: {e}")

def main():
    driver = None
    try:
        driver = setup_driver()
        login_to_production(driver)
        
        # First search on main page
        found = find_laniado_prod_element(driver)
        
        # Try navigating to Cardex
        navigate_to_cardex(driver)
        
        # Search again in Cardex
        print("\n🔍 Searching for LaniadoProd in Cardex page...")
        found = find_laniado_prod_element(driver)
        
        # Print entire page structure around "Laniado"
        print("\n📄 Scanning entire page structure for context...")
        try:
            # Find all elements with "Laniado" text
            all_elements = driver.find_elements(By.XPATH, "//*")
            laniado_elements = []
            
            for elem in all_elements:
                try:
                    if 'laniado' in elem.text.lower() or 'production' in elem.text.lower():
                        laniado_elements.append({
                            'tag': elem.tag_name,
                            'text': elem.text[:50],  # First 50 chars
                            'class': elem.get_attribute('class'),
                        })
                except:
                    pass
            
            if laniado_elements:
                print("📍 Elements containing 'Laniado' or 'Production':")
                for i, elem in enumerate(laniado_elements[:10]):  # Show first 10
                    print(f"   {i+1}. {elem['tag']} - Text: {elem['text']} - Class: {elem['class']}")
        except:
            pass
        
        print("\n✨ Search complete! Check above for LaniadoProd element details")
        
    except Exception as e:
        print(f"❌ Error: {e}")
    finally:
        if driver:
            print("\n👋 Closing browser...")
            time.sleep(2)
            driver.quit()

if __name__ == "__main__":
    main()
