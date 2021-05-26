@echo off
for /d %%i in ("C:\Users*") DO (
echo Eliminando C:\Users\%%~nxi\AppData\Local\Temp
rd "C:\Users\%%~nxi\AppData\Local\Temp" /s /q
echo Eliminando C:\Users\%%~nxi\AppData\Local\Microsoft\Terminal Server Client\Cache
rd "C:\Users\%%~nxi\AppData\Local\Microsoft\Terminal Server Client\Cache" /s /q
echo Eliminando C:\Users\%%~nxi\AppData\Local\Microsoft\Windows\Temporary Internet Files
rd "C:\Users\%%~nxi\AppData\Local\Microsoft\Windows\Temporary Internet Files" /s /q
echo Eliminando C:\Users\%%~nxi\AppData\Local\Google\Chrome\User Data\Default\Cache
rd "C:\Users\%%~nxi\AppData\Local\Google\Chrome\User Data\Default\Cache" /s /q
)
pause