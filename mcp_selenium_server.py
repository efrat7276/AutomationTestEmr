#!/usr/bin/env python3
"""
Selenium MCP Server for Claude
Provides tools to interact with web applications via Selenium
"""

import asyncio
import json
import logging
from typing import Any

from mcp.server import Server
from mcp.types import Tool, TextContent
from mcp.server.models import ToolResult

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
import time

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Global driver instance
driver = None

class SeleniumMCPServer:
    def __init__(self):
        self.server = Server("selenium-mcp-server")
        self.driver = None
        self.setup_handlers()
        
    def setup_handlers(self):
        """Setup MCP tool handlers"""
        
        @self.server.call_tool()
        async def handle_tool_call(name: str, arguments: dict):
            logger.info(f"🔧 Tool called: {name} with args: {arguments}")
            
            if name == "open_browser":
                return await self.open_browser(arguments)
            elif name == "navigate":
                return await self.navigate(arguments)
            elif name == "login":
                return await self.login(arguments)
            elif name == "find_element":
                return await self.find_element(arguments)
            elif name == "find_elements":
                return await self.find_elements(arguments)
            elif name == "get_page_source":
                return await self.get_page_source()
            elif name == "close_browser":
                return await self.close_browser()
            else:
                return ToolResult(content=[TextContent(type="text", text=f"❌ Unknown tool: {name}")])
        
        @self.server.list_tools()
        async def list_tools():
            return [
                Tool(
                    name="open_browser",
                    description="Open Chrome browser",
                    inputSchema={
                        "type": "object",
                        "properties": {
                            "headless": {
                                "type": "boolean",
                                "description": "Run in headless mode",
                                "default": False
                            }
                        }
                    }
                ),
                Tool(
                    name="navigate",
                    description="Navigate to a URL",
                    inputSchema={
                        "type": "object",
                        "properties": {
                            "url": {
                                "type": "string",
                                "description": "The URL to navigate to"
                            }
                        },
                        "required": ["url"]
                    }
                ),
                Tool(
                    name="login",
                    description="Fill login form and submit",
                    inputSchema={
                        "type": "object",
                        "properties": {
                            "username": {"type": "string"},
                            "password": {"type": "string"},
                            "username_id": {"type": "string", "default": "user_name"},
                            "password_id": {"type": "string", "default": "password"},
                            "submit_id": {"type": "string", "default": "submitBtn"}
                        },
                        "required": ["username", "password"]
                    }
                ),
                Tool(
                    name="find_element",
                    description="Find an element by XPath and return its properties",
                    inputSchema={
                        "type": "object",
                        "properties": {
                            "xpath": {
                                "type": "string",
                                "description": "XPath to find element"
                            }
                        },
                        "required": ["xpath"]
                    }
                ),
                Tool(
                    name="find_elements",
                    description="Find all elements matching XPath pattern",
                    inputSchema={
                        "type": "object",
                        "properties": {
                            "xpath": {
                                "type": "string",
                                "description": "XPath pattern"
                            }
                        },
                        "required": ["xpath"]
                    }
                ),
                Tool(
                    name="get_page_source",
                    description="Get current page HTML source",
                    inputSchema={"type": "object", "properties": {}}
                ),
                Tool(
                    name="close_browser",
                    description="Close the browser",
                    inputSchema={"type": "object", "properties": {}}
                )
            ]
    
    async def open_browser(self, args: dict) -> ToolResult:
        """Open Chrome browser"""
        try:
            headless = args.get("headless", False)
            options = webdriver.ChromeOptions()
            
            if not headless:
                options.add_argument("--start-maximized")
            else:
                options.add_argument("--headless")
            
            options.add_argument("--disable-blink-features=AutomationControlled")
            options.add_experimental_option("excludeSwitches", ["enable-automation"])
            options.add_experimental_option("useAutomationExtension", False)
            
            service = Service(ChromeDriverManager().install())
            self.driver = webdriver.Chrome(service=service, options=options)
            
            logger.info("✅ Browser opened successfully")
            return ToolResult(content=[TextContent(type="text", text="✅ Browser opened")])
        except Exception as e:
            logger.error(f"❌ Error opening browser: {e}")
            return ToolResult(content=[TextContent(type="text", text=f"❌ Error: {str(e)}")])
    
    async def navigate(self, args: dict) -> ToolResult:
        """Navigate to URL"""
        try:
            if not self.driver:
                return ToolResult(content=[TextContent(type="text", text="❌ Browser not open. Call open_browser first")])
            
            url = args["url"]
            self.driver.get(url)
            await asyncio.sleep(3)
            
            logger.info(f"✅ Navigated to {url}")
            return ToolResult(content=[TextContent(type="text", text=f"✅ Navigated to {url}")])
        except Exception as e:
            logger.error(f"❌ Navigation error: {e}")
            return ToolResult(content=[TextContent(type="text", text=f"❌ Error: {str(e)}")])
    
    async def login(self, args: dict) -> ToolResult:
        """Fill login form"""
        try:
            if not self.driver:
                return ToolResult(content=[TextContent(type="text", text="❌ Browser not open")])
            
            username = args["username"]
            password = args["password"]
            username_id = args.get("username_id", "user_name")
            password_id = args.get("password_id", "password")
            submit_id = args.get("submit_id", "submitBtn")
            
            # Fill username
            username_field = WebDriverWait(self.driver, 10).until(
                EC.presence_of_element_located((By.ID, username_id))
            )
            username_field.clear()
            username_field.send_keys(username)
            
            # Fill password
            password_field = self.driver.find_element(By.ID, password_id)
            password_field.clear()
            password_field.send_keys(password)
            
            # Submit
            submit_btn = self.driver.find_element(By.ID, submit_id)
            submit_btn.click()
            
            await asyncio.sleep(5)
            
            logger.info(f"✅ Login submitted for user: {username}")
            return ToolResult(content=[TextContent(type="text", text=f"✅ Login submitted for {username}")])
        except Exception as e:
            logger.error(f"❌ Login error: {e}")
            return ToolResult(content=[TextContent(type="text", text=f"❌ Error: {str(e)}")])
    
    async def find_element(self, args: dict) -> ToolResult:
        """Find element by XPath"""
        try:
            if not self.driver:
                return ToolResult(content=[TextContent(type="text", text="❌ Browser not open")])
            
            xpath = args["xpath"]
            element = WebDriverWait(self.driver, 5).until(
                EC.presence_of_element_located((By.XPATH, xpath))
            )
            
            result = {
                "found": True,
                "tag": element.tag_name,
                "text": element.text[:100] if element.text else "",
                "class": element.get_attribute("class"),
                "id": element.get_attribute("id"),
                "xpath": xpath
            }
            
            logger.info(f"✅ Found element: {result}")
            return ToolResult(content=[TextContent(type="text", text=json.dumps(result, indent=2, ensure_ascii=False))])
        except Exception as e:
            logger.error(f"❌ Find element error: {e}")
            return ToolResult(content=[TextContent(type="text", text=f"❌ Element not found: {str(e)}")])
    
    async def find_elements(self, args: dict) -> ToolResult:
        """Find multiple elements by XPath"""
        try:
            if not self.driver:
                return ToolResult(content=[TextContent(type="text", text="❌ Browser not open")])
            
            xpath = args["xpath"]
            elements = self.driver.find_elements(By.XPATH, xpath)
            
            results = []
            for i, elem in enumerate(elements[:10]):  # Limit to 10
                results.append({
                    "index": i,
                    "tag": elem.tag_name,
                    "text": elem.text[:50] if elem.text else "",
                    "class": elem.get_attribute("class")
                })
            
            logger.info(f"✅ Found {len(elements)} elements, returning first 10")
            return ToolResult(content=[TextContent(type="text", text=json.dumps({
                "total": len(elements),
                "elements": results
            }, indent=2, ensure_ascii=False))])
        except Exception as e:
            logger.error(f"❌ Find elements error: {e}")
            return ToolResult(content=[TextContent(type="text", text=f"❌ Error: {str(e)}")])
    
    async def get_page_source(self) -> ToolResult:
        """Get page source"""
        try:
            if not self.driver:
                return ToolResult(content=[TextContent(type="text", text="❌ Browser not open")])
            
            source = self.driver.page_source
            
            # Search for LaniadoProd
            if "LaniadoProd" in source:
                logger.info("✅ 'LaniadoProd' found in page source!")
                return ToolResult(content=[TextContent(type="text", text="✅ 'LaniadoProd' found in page source")])
            elif "Laniado" in source:
                logger.info("✅ 'Laniado' found in page source")
                return ToolResult(content=[TextContent(type="text", text="✅ 'Laniado' found in page source")])
            else:
                return ToolResult(content=[TextContent(type="text", text="❌ 'Laniado' not found in page")])
        except Exception as e:
            logger.error(f"❌ Page source error: {e}")
            return ToolResult(content=[TextContent(type="text", text=f"❌ Error: {str(e)}")])
    
    async def close_browser(self) -> ToolResult:
        """Close browser"""
        try:
            if self.driver:
                self.driver.quit()
                self.driver = None
                logger.info("✅ Browser closed")
                return ToolResult(content=[TextContent(type="text", text="✅ Browser closed")])
            else:
                return ToolResult(content=[TextContent(type="text", text="❌ Browser not open")])
        except Exception as e:
            logger.error(f"❌ Close browser error: {e}")
            return ToolResult(content=[TextContent(type="text", text=f"❌ Error: {str(e)}")])
    
    async def run(self):
        """Run the MCP server"""
        logger.info("🚀 Starting Selenium MCP Server...")
        async with self.server:
            logger.info("✅ Server ready! Waiting for connections...")
            await asyncio.Event().wait()

if __name__ == "__main__":
    server = SeleniumMCPServer()
    try:
        asyncio.run(server.run())
    except KeyboardInterrupt:
        logger.info("👋 Server stopped by user")
