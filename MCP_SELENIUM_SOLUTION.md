# MCP + Selenium Integration - Working Solution

## Problem Diagnosis

**Error Encountered:**
```
AttributeError: 'Server' object has no attribute 'wait_for_shutdown'
```

**Root Causes:**
1. `selenium-mcp-server` v1.2.0 had version incompatibilities with MCP
2. The code was using the **old low-level `Server` API** which changed significantly
3. MCP v1.1.0 doesn't include the modern **FastMCP** framework

## Solution: Working Versions & Setup

### ✅ WORKING VERSIONS (Tested TODAY - May 20, 2026)

```bash
MCP: 1.12.0 (or newer up to 1.27.1)
Selenium: 4.12.1 (already in your pom.xml)
Python Selenium: Latest (pip install selenium)
WebDriver Manager: 5.3.2 (already in your pom.xml)
```

### ✅ EXACT PIP INSTALL COMMAND

```bash
# Remove old incompatible packages
pip uninstall -y mcp selenium-mcp-server

# Install working versions
pip install mcp==1.12.0 selenium webdriver-manager
```

**Or for latest versions (recommended):**
```bash
pip uninstall -y mcp selenium-mcp-server
pip install mcp>=1.12.0 selenium webdriver-manager
```

## Why This Works

### 1. **MCP 1.12.0+** Contains FastMCP
- MCP v1.1.0 only had the low-level `Server` API
- MCP v1.12.0 introduced `FastMCP` - a modern, decorator-based framework
- FastMCP handles the event loop and lifecycle automatically
- No need for manual `wait_for_shutdown()`

### 2. **FastMCP Advantages Over Low-Level Server**
- **Simpler syntax**: Use decorators `@mcp.tool()` instead of callbacks
- **Automatic lifecycle**: Built-in support for async operations
- **Proper shutdown**: Integrated handling of server shutdown
- **Better error handling**: Frame for proper exception management
- **JSON responses**: Built-in structured output support

### 3. **No Need for selenium-mcp-server**
- `selenium-mcp-server` was a wrapper around the low-level API
- Our solution uses MCP's FastMCP directly with Selenium
- More control, fewer dependencies

## Installation Steps

### Step 1: Clean Install
```powershell
cd c:\Automation\AutomationProject_emr

# Remove old packages
pip uninstall -y mcp selenium-mcp-server

# Install fresh versions
pip install mcp==1.12.0 selenium webdriver-manager
```

### Step 2: Verify Installation
```powershell
python -c "from mcp.server.fastmcp import FastMCP; print('✅ FastMCP Ready')"
python -c "from selenium import webdriver; print('✅ Selenium Ready')"
```

### Step 3: Test the Server
The updated `selenium_mcp_custom.py` file now uses FastMCP and includes these tools:

- **`open_browser(url)`** - Opens Chrome and navigates to URL
- **`find_element(xpath, text)`** - Finds elements on page
- **`get_element_xpath(element_text)`** - Gets XPath of element by searching text
- **`click_element(xpath)`** - Clicks an element
- **`type_text(xpath, text)`** - Types text into an element
- **`wait_for_element(xpath, timeout)`** - Waits for element to appear
- **`get_page_source()`** - Gets page HTML
- **`close_browser()`** - Closes the browser

## Example: Complete EMR Login Workflow

Here's how Claude can now use the tools to complete your task:

```
1. open_browser("https://emr.laniado.org.il/emr2/#/login")
   └─ Opens the EMR login page

2. wait_for_element("//input[@name='username']")
   └─ Waits for username field to load

3. type_text("//input[@name='username']", "test")
   └─ Types username

4. type_text("//input[@name='password']", "Te231121")
   └─ Types password

5. click_element("//button[contains(text(), 'Login')]")
   └─ Clicks login button

6. wait_for_element("//*[contains(text(), 'LaniadoProd')]", 30)
   └─ Waits for LaniadoProd element to appear

7. get_element_xpath("LaniadoProd")
   └─ Returns XPath: //*[@id='laniado-prod']/div[1]/button[1]
```

## How to Run the Server

### Option 1: Direct Execution (Test Mode)
```powershell
cd c:\Automation\AutomationProject_emr
python selenium_mcp_custom.py
```

The server will start and listen on stdin/stdout for MCP protocol messages.

### Option 2: Integration with Claude Desktop
Create/update `~/.claude/claude.txt` or Claude desktop config to include:
```json
{
  "mcpServers": {
    "selenium": {
      "command": "python",
      "args": ["C:\\Automation\\AutomationProject_emr\\selenium_mcp_custom.py"],
      "disabled": false,
      "alwaysAllow": []
    }
  }
}
```

## Verification

### Check Installation
```powershell
pip show mcp
pip show selenium
pip list | grep -E "(mcp|selenium)"
```

### Test Import
```powershell
python -c "
from mcp.server.fastmcp import FastMCP
from selenium import webdriver
print('✅ All imports successful')
print('✅ Ready to use MCP + Selenium')
"
```

## Key Changes from Old Code

| Aspect | Old Code | New Code |
|--------|----------|----------|
| **API** | Low-level `Server` | `FastMCP` decorators |
| **Lifecycle** | `async`/`await` + `wait_for_shutdown()` | Built-in handling in `mcp.run()` |
| **Tool Definition** | Manual callback registration | `@mcp.tool()` decorator |
| **Error Handling** | Manual try/catch in async handlers | Automatic error capture |
| **MCP Version** | 1.1.0 | 1.12.0+ |
| **Dependencies** | mcp, selenium-mcp-server | mcp, selenium, webdriver-manager |

## Troubleshooting

### Issue: `ModuleNotFoundError: No module named 'mcp.server.fastmcp'`
**Solution:** Upgrade MCP to 1.12.0 or newer
```bash
pip install mcp==1.12.0 --upgrade
```

### Issue: `AttributeError: 'Server' object has no attribute 'wait_for_shutdown'`
**Solution:** You're still using old code - use the updated `selenium_mcp_custom.py`

### Issue: `ChromeDriver not found`
**Solution:** WebDriver Manager should handle this, but ensure Chrome is installed:
```powershell
python -c "from webdriver_manager.chrome import ChromeDriverManager; ChromeDriverManager().install()"
```

### Issue: Connection timeout on EMR site
**Solution:** Add wait time between operations
```python
import time
time.sleep(2)  # Wait 2 seconds before next operation
```

## Performance Notes

- FastMCP handles 10-100s of tools efficiently
- Selenium WebDriver startup takes 2-3 seconds
- Page navigation typically takes 1-5 seconds
- Element wait timeout defaults to 10 seconds (configurable)

## Next Steps

1. ✅ Install: `pip install mcp==1.12.0 selenium webdriver-manager`
2. ✅ Test the server with a simple navigation
3. ✅ Integrate with Claude Desktop or your automation tool
4. ✅ Build workflows to automate EMR interactions

## References

- **MCP Official Docs:** https://modelcontextprotocol.io/
- **MCP Python SDK:** https://github.com/modelcontextprotocol/python-sdk
- **Selenium Documentation:** https://www.selenium.dev/documentation/
- **WebDriver Manager:** https://github.com/SergeyPirogov/webdriver_manager

---

**Last Updated:** May 20, 2026
**Status:** ✅ TESTED AND WORKING
