@echo off
chcp 1252

rem ---------------------------------------------------------------------------
rem Script de parada del Servidor CATALINA
rem ---------------------------------------------------------------------------

setlocal

rem Adivina CATALINA_HOME si no está definida
set "CURRENT_DIR=%cd%"
if not "%CATALINA_HOME%" == "" goto gotHome
set "CATALINA_HOME=%CURRENT_DIR%"
if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
cd ..
set "CATALINA_HOME=%cd%"
cd "%CURRENT_DIR%"
:gotHome
if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
echo La variable de entorno CATALINA_HOME no está definida correctamente.
echo Se necesita esta variable de entorno para ejecutar este programa
goto end
:okHome

set "EXECUTABLE=%CATALINA_HOME%\bin\catalina.bat"

rem Comprueba que el ejecutable destino exista
if exist "%EXECUTABLE%" goto okExec
echo No se encuentra  "%EXECUTABLE%"
echo Este fichero es necesario para ejecutar este programa
goto end
:okExec

rem Se obtienen los argumentos restantes de la línea de comandos sin modificar y se guardan
set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

call "%EXECUTABLE%" stop %CMD_LINE_ARGS%

:end
