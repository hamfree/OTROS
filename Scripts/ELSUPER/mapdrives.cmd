PowerShell -Command "Set-ExecutionPolicy -Scope CurrentUser Unrestricted" >> "%TEMP%\StartupLog.txt" 2>&1 
PowerShell -File "d:\des\bin\scripts\mapdrives.ps1" >> "%TEMP%\StartupLog.txt" 2>&1