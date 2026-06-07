#!/usr/bin/env python3
"""
MCP Server for Selenium WebDriver - FastMCP Version
Enables Claude to interact with web applications
"""

import logging
import time
from mcp.server.fastmcp import FastMCP
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager

# Set up logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("selenium_mcp")

# Global driver instance
driver = None
wait = None

# Create FastMCP server
mcp = FastMCP("Selenium WebDriver", json_response=True)


@mcp.tool()
def open_browser(url: str) -> str:
    """Open a browser and navigate to URL"""
    global driver, wait
    try:
        options = webdriver.ChromeOptions()
        options.add_argument("--start-maximized")
        service = Service(ChromeDriverManager().install())
        driver = webdriver.Chrome(service=service, options=options)
        wait = WebDriverWait(driver, 10)
        
        driver.get(url)
        logger.info(f"Browser opened and navigated to {url}")
        return f"✅ Browser opened at {url}"
    except Exception as e:
        logger.error(f"Error opening browser: {str(e)}")
        return f"❌ Error opening browser: {str(e)}"


@mcp.tool()
def find_element(xpath: str = None, text: str = None) -> str:
    """Find element on page by XPath or text"""
    global driver
    try:
        if not driver:
            return "❌ Browser not opened. Call open_browser first."
        
        if xpath:
            elements = driver.find_elements(By.XPATH, xpath)
            if elements:
                # Return XPath and element info
                elem = elements[0]
                return f"✅ Found {len(elements)} element(s) with XPath: {xpath}\nElement text: {elem.text}\nTag: {elem.tag_name}"
            else:
                return f"❌ No elements found with XPath: {xpath}"
        
        if text:
            xpath_text = f"//*[contains(text(), '{text}')]"
            elements = driver.find_elements(By.XPATH, xpath_text)
            if elements:
                return f"✅ Found {len(elements)} element(s) containing text: '{text}'"
            else:
                return f"❌ No elements found with text: '{text}'"
        
        return "⚠️ Please provide either xpath or text parameter"
    except Exception as e:
        logger.error(f"Error finding element: {str(e)}")
        return f"❌ Error: {str(e)}"


@mcp.tool()
def get_element_xpath(element_text: str) -> str:
    """Get XPath of an element by searching for its text"""
    global driver
    try:
        if not driver:
            return "❌ Browser not opened. Call open_browser first."
        
        xpath = f"//*[contains(text(), '{element_text}')]"
        elements = driver.find_elements(By.XPATH, xpath)
        
        if elements:
            elem = elements[0]
            # Generate a more specific XPath
            tag = elem.tag_name
            elem_xpath = driver.execute_script(
                """
                function getElementXPath(element) {
                    if (element.id !== '')
                        return "//*[@id='" + element.id + "']";
                    if (element === document.body)
                        return "/body";
                    
                    var ix = 0;
                    var siblings = element.parentNode.childNodes;
                    for (var i = 0; i < siblings.length; i++) {
                        var sibling = siblings[i];
                        if (sibling === element)
                            return getElementXPath(element.parentNode) + "/" + element.tagName.toLowerCase() + "[" + (ix + 1) + "]";
                        if (sibling.nodeType === 1 && sibling.tagName.toLowerCase() === element.tagName.toLowerCase())
                            ix++;
                    }
                }
                return getElementXPath(arguments[0]);
                """,
                elem
            )
            return f"✅ Found element '{element_text}'\nXPath: {elem_xpath}\nTag: {tag}\nText: {elem.text}"
        else:
            return f"❌ No element found with text: '{element_text}'"
    except Exception as e:
        logger.error(f"Error getting element XPath: {str(e)}")
        return f"❌ Error: {str(e)}"


@mcp.tool()
def click_element(xpath: str) -> str:
    """Click on an element"""
    global driver, wait
    try:
        if not driver:
            return "❌ Browser not opened"
        
        element = wait.until(EC.element_to_be_clickable((By.XPATH, xpath)))
        element.click()
        logger.info(f"Clicked element with XPath: {xpath}")
        return f"✅ Clicked element with XPath: {xpath}"
    except Exception as e:
        logger.error(f"Error clicking element: {str(e)}")
        return f"❌ Error clicking element: {str(e)}"


@mcp.tool()
def type_text(xpath: str, text: str) -> str:
    """Type text into an element"""
    global driver, wait
    try:
        if not driver:
            return "❌ Browser not opened"
        
        element = wait.until(EC.presence_of_element_located((By.XPATH, xpath)))
        element.clear()
        element.send_keys(text)
        logger.info(f"Typed '{text}' into element")
        return f"✅ Typed '{text}' into element"
    except Exception as e:
        logger.error(f"Error typing text: {str(e)}")
        return f"❌ Error typing text: {str(e)}"


@mcp.tool()
def wait_for_element(xpath: str, timeout: int = 10) -> str:
    """Wait for element to be present on page"""
    global driver, wait
    try:
        if not driver:
            return "❌ Browser not opened"
        
        custom_wait = WebDriverWait(driver, timeout)
        custom_wait.until(EC.presence_of_element_located((By.XPATH, xpath)))
        logger.info(f"Element found: {xpath}")
        return f"✅ Element is present: {xpath}"
    except Exception as e:
        logger.error(f"Error waiting for element: {str(e)}")
        return f"❌ Element not found or timeout: {str(e)}"


@mcp.tool()
def get_page_source() -> str:
    """Get current page HTML source"""
    global driver
    try:
        if not driver:
            return "❌ Browser not opened"
        
        source = driver.page_source
        return f"✅ Page source retrieved ({len(source)} characters)\n\n{source[:2000]}..."
    except Exception as e:
        logger.error(f"Error getting page source: {str(e)}")
        return f"❌ Error: {str(e)}"


@mcp.tool()
def close_browser() -> str:
    """Close the browser"""
    global driver
    try:
        if driver:
            driver.quit()
            driver = None
            logger.info("Browser closed")
            return "✅ Browser closed"
        else:
            return "⚠️ Browser not open"
    except Exception as e:
        logger.error(f"Error closing browser: {str(e)}")
        return f"❌ Error: {str(e)}"


def main():
    """Entry point for the MCP server"""
    logger.info("🚀 Starting Selenium MCP Server...")
    mcp.run(transport="stdio")


if __name__ == "__main__":
    main()
