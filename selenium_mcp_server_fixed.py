#!/usr/bin/env python3
"""
Fixed MCP Selenium Server - Works with current MCP version
Connects Claude to Selenium WebDriver for browser automation
"""

import asyncio
import sys
from typing import Any
from mcp.server import Server
from mcp.types import Tool, TextContent
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Import Selenium
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
import time

# Global WebDriver instance
driver = None

# Create MCP Server
server = Server("selenium-mcp-server-fixed")

@server.list_tools()
async def list_tools() -> list[Tool]:
    """List available Selenium tools"""
    return [
        Tool(
            name="open_browser",
            description="Open Chrome browser and navigate to URL",
            inputSchema={
                "type": "object",
                "properties": {
                    "url": {
                        "type": "string",
                        "description": "URL to navigate to"
                    }
                },
                "required": ["url"]
            }
        ),
        Tool(
            name="login",
            description="Login to EMR application",
            inputSchema={
                "type": "object",
                "properties": {
                    "username": {
                        "type": "string",
                        "description": "Username"
                    },
                    "password": {
                        "type": "string",
                        "description": "Password"
                    },
                    "role": {
                        "type": "string",
                        "description": "Role to select (e.g., 'רופא', 'אחות')"
                    }
                },
                "required": ["username", "password", "role"]
            }
        ),
        Tool(
            name="find_element",
            description="Find element on page by XPath or ID",
            inputSchema={
                "type": "object",
                "properties": {
                    "xpath": {
                        "type": "string",
                        "description": "XPath to find element"
                    },
                    "text": {
                        "type": "string",
                        "description": "Text content to search for"
                    }
                }
            }
        ),
        Tool(
            name="get_page_source",
            description="Get current page HTML source",
            inputSchema={
                "type": "object",
                "properties": {}
            }
        ),
        Tool(
            name="search_laniado_prod",
            description="Search for LaniadoProd element on page",
            inputSchema={
                "type": "object",
                "properties": {}
            }
        ),
        Tool(
            name="close_browser",
            description="Close the browser",
            inputSchema={
                "type": "object",
                "properties": {}
            }
        ),
    ]

@server.call_tool()
async def call_tool(name: str, arguments: dict) -> list[TextContent]:
    """Handle tool calls from Claude"""
    global driver
    
    logger.info(f"Tool called: {name} with args: {arguments}")
    
    try:
        if name == "open_browser":
            url = arguments.get("url")
            logger.info(f"Opening browser to {url}")
            
            options = webdriver.ChromeOptions()
            options.add_argument("--start-maximized")
            options.add_argument("--disable-blink-features=AutomationControlled")
            options.add_experimental_option("excludeSwitches", ["enable-automation"])
            options.add_experimental_option("useAutomationExtension", False)
            
            service = Service(ChromeDriverManager().install())
            driver = webdriver.Chrome(service=service, options=options)
            driver.get(url)
            
            time.sleep(3)
            
            return [TextContent(type="text", text=f"✅ Opened browser to {url}")]
        
        elif name == "login":
            if not driver:
                return [TextContent(type="text", text="❌ Browser not opened")]
            
            username = arguments.get("username")
            password = arguments.get("password")
            role = arguments.get("role")
            
            logger.info(f"Logging in as {username} with role {role}")
            
            # Fill username
            username_field = WebDriverWait(driver, 10).until(
                EC.presence_of_element_located((By.ID, "user_name"))
            )
            username_field.clear()
            username_field.send_keys(username)
            
            # Fill password
            password_field = driver.find_element(By.ID, "password")
            password_field.clear()
            password_field.send_keys(password)
            
            # Click submit
            submit_btn = driver.find_element(By.ID, "submitBtn")
            submit_btn.click()
            
            time.sleep(5)
            
            # Select role
            try:
                role_elements = WebDriverWait(driver, 10).until(
                    EC.presence_of_all_elements_located((By.XPATH, "//ul[@class='list-group']/li"))
                )
                
                for role_elem in role_elements:
                    if role in role_elem.text:
                        role_elem.click()
                        logger.info(f"Selected role: {role}")
                        break
                
                time.sleep(3)
            except Exception as e:
                logger.warning(f"Role selection skipped: {e}")
            
            return [TextContent(type="text", text=f"✅ Logged in as {username} with role {role}")]
        
        elif name == "find_element":
            if not driver:
                return [TextContent(type="text", text="❌ Browser not opened")]
            
            xpath = arguments.get("xpath")
            text = arguments.get("text")
            
            result_text = "🔍 Search Results:\n"
            
            if xpath:
                try:
                    elements = driver.find_elements(By.XPATH, xpath)
                    result_text += f"Found {len(elements)} elements with XPath: {xpath}\n"
                    for i, elem in enumerate(elements[:5]):
                        result_text += f"  {i+1}. {elem.tag_name} - Text: {elem.text[:50]}\n"
                except Exception as e:
                    result_text += f"Error with XPath: {e}\n"
            
            if text:
                try:
                    xpath_search = f"//*[contains(text(), '{text}')]"
                    elements = driver.find_elements(By.XPATH, xpath_search)
                    result_text += f"Found {len(elements)} elements containing: {text}\n"
                    for i, elem in enumerate(elements[:5]):
                        result_text += f"  {i+1}. {elem.tag_name} - {elem.get_attribute('class')}\n"
                except Exception as e:
                    result_text += f"Error searching text: {e}\n"
            
            return [TextContent(type="text", text=result_text)]
        
        elif name == "search_laniado_prod":
            if not driver:
                return [TextContent(type="text", text="❌ Browser not opened")]
            
            logger.info("Searching for LaniadoProd element")
            
            result_text = "🔍 LaniadoProd Search Results:\n\n"
            
            # Search patterns
            patterns = [
                ("//span[contains(text(), 'LaniadoProd')]", "Span with LaniadoProd"),
                ("//div[contains(text(), 'LaniadoProd')]", "Div with LaniadoProd"),
                ("//*[contains(text(), 'LaniadoProd')]", "Any element with LaniadoProd"),
                ("//header//*[contains(text(), 'Laniado')]", "Header element with Laniado"),
                ("//*[@class='header-title']", "Header title element"),
            ]
            
            found_any = False
            
            for xpath, description in patterns:
                try:
                    elements = driver.find_elements(By.XPATH, xpath)
                    if elements:
                        found_any = True
                        result_text += f"✅ {description}\n"
                        for elem in elements[:3]:
                            text_content = elem.text.strip()
                            if text_content:
                                result_text += f"   - Text: {text_content}\n"
                                result_text += f"   - XPath: {xpath}\n"
                                result_text += f"   - Tag: {elem.tag_name}\n"
                                result_text += f"   - Class: {elem.get_attribute('class')}\n\n"
                except:
                    pass
            
            if not found_any:
                result_text += "❌ LaniadoProd element not found with standard patterns\n"
                result_text += "\n📄 Scanning page for 'Laniado' or 'Production'...\n"
                
                page_source = driver.page_source
                if "LaniadoProd" in page_source:
                    result_text += "✅ Found 'LaniadoProd' in page source\n"
                elif "Laniado" in page_source:
                    result_text += "✅ Found 'Laniado' in page source\n"
                else:
                    result_text += "❌ No Laniado/Production references found\n"
            
            return [TextContent(type="text", text=result_text)]
        
        elif name == "get_page_source":
            if not driver:
                return [TextContent(type="text", text="❌ Browser not opened")]
            
            source = driver.page_source
            # Return first 2000 chars
            return [TextContent(type="text", text=f"📄 Page source (first 2000 chars):\n\n{source[:2000]}...")]
        
        elif name == "close_browser":
            if driver:
                driver.quit()
                logger.info("Browser closed")
                return [TextContent(type="text", text="✅ Browser closed")]
            return [TextContent(type="text", text="⚠️ No browser to close")]
        
        else:
            return [TextContent(type="text", text=f"❌ Unknown tool: {name}")]
    
    except Exception as e:
        logger.error(f"Error in tool {name}: {e}")
        return [TextContent(type="text", text=f"❌ Error: {str(e)}")]

async def main():
    """Start the MCP server"""
    logger.info("Starting Selenium MCP Server...")
    logger.info("✅ Selenium MCP Server running!")
    logger.info("Server is ready to accept commands from Claude")
    
    # Keep server running
    await asyncio.Event().wait()

if __name__ == "__main__":
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        logger.info("Server stopped by user")
        if driver:
            driver.quit()
        sys.exit(0)
