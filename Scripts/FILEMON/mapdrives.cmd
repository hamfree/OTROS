PowerShell -Command "Set-ExecutionPolicy -Scope CurrentUser Unrestricted" >> "%USERPROFILE%\StartupLog.txt" 2>&1 
PowerShell -File "c:\des\bin\scripts\mapdrives.ps1" >> "%TEMP%\StartupLog.txt" 2>&1