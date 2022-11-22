@echo off
cls
chcp 1252
mode con cols=80 lines=25
echo **************************************************************************
echo *            Eliminacion de procesos para ahorrar bateria                *
echo **************************************************************************
echo.
taskkill /f /im dropbox.exe /t
taskkill /f /im OneDrive.exe /t
taskkill /f /im Qsync.exe /t
taskkill /f /im Skype.exe /t
taskkill /f /im bridge.exe /t