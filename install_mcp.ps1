#!/usr/bin/env powershell
# MCP + Selenium Installation Script
# Run this script to set up the working environment

Write-Host "🔄 MCP + Selenium Setup Script" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Uninstall incompatible packages
Write-Host "📦 Step 1: Removing incompatible packages..." -ForegroundColor Yellow
pip uninstall -y mcp selenium-mcp-server 2>$null
Write-Host "✅ Removed old packages" -ForegroundColor Green
Write-Host ""

# Step 2: Install working versions
Write-Host "📦 Step 2: Installing MCP 1.12.0 and dependencies..." -ForegroundColor Yellow
pip install mcp==1.12.0 selenium webdriver-manager -q
Write-Host "✅ Installed working versions" -ForegroundColor Green
Write-Host ""

# Step 3: Verify MCP installation
Write-Host "✔️  Step 3: Verifying MCP installation..." -ForegroundColor Yellow
$mcpVersion = pip show mcp | Select-String "Version" | % {$_.ToString().Split(": ")[1]}
Write-Host "  MCP Version: $mcpVersion" -ForegroundColor Cyan

# Step 4: Verify Selenium installation
$seleniumVersion = pip show selenium | Select-String "Version" | % {$_.ToString().Split(": ")[1]}
Write-Host "  Selenium Version: $seleniumVersion" -ForegroundColor Cyan

# Step 5: Test imports
Write-Host ""
Write-Host "✔️  Step 4: Testing imports..." -ForegroundColor Yellow
$result = python -c "from mcp.server.fastmcp import FastMCP; from selenium import webdriver; print('✅')" 2>&1
if ($result -match "✅") {
    Write-Host "  FastMCP: ✅ Ready" -ForegroundColor Green
    Write-Host "  Selenium: ✅ Ready" -ForegroundColor Green
}
else {
    Write-Host "  ❌ Import test failed" -ForegroundColor Red
    Write-Host "  Output: $result" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "✅ Setup Complete!" -ForegroundColor Green
Write-Host ""
Write-Host "📝 Next Steps:" -ForegroundColor Cyan
Write-Host "  1. Run the MCP server:" -ForegroundColor White
Write-Host "     python selenium_mcp_custom.py" -ForegroundColor Gray
Write-Host ""
Write-Host "  2. Use tools to interact with web applications:" -ForegroundColor White
Write-Host "     - open_browser(url)" -ForegroundColor Gray
Write-Host "     - find_element(xpath, text)" -ForegroundColor Gray
Write-Host "     - click_element(xpath)" -ForegroundColor Gray
Write-Host "     - type_text(xpath, text)" -ForegroundColor Gray
Write-Host ""
Write-Host "📚 Documentation:" -ForegroundColor Cyan
Write-Host "  - MCP_SOLUTION.md (detailed guide)" -ForegroundColor Gray
Write-Host "  - MCP_QUICK_START.md (quick reference)" -ForegroundColor Gray
Write-Host ""
