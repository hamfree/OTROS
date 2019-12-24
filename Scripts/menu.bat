@echo off
echo ########################################################
echo .
echo . menu.bat Meny para ejecucion sencilla de tareas
echo . (C) Cristobal Gonzalez Almiron 2014
echo .
echo ########################################################
echo .



rem ########################################################

rem #### Subcommands execution
IF NOT "%1"=="" GOTO subcomandos

rem ########################################################


:menu_start
rem Variables actuales
rem 

set APP=

echo .
echo . -------------------------------------------
echo .    Menu JDYNAMICS
echo . -------------------------------------------
set OPTION=1
SET CHOICE=

:menu
set CHOICES=
rem only for debbuging
rem echo CHOICE: %CHOICE%
rem  OPTION: %OPTION%

rem ########################################################
rem Opciones de menu
rem ########################################################



set LABEL=compilarLimpio
set TEXT=Compilar proyecto
set KEY=0
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" echo OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" start %CD%\menu.bat %LABEL%
if "%OPTION%"=="%CHOICE%" goto menu_start
set CHOICES=%CHOICES%%KEY%
set /a "OPTION+=1"

set LABEL=ejecutar
set TEXT=Ejecutar proyecto
set KEY=r
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" echo OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" start %CD%\menu.bat %LABEL%
if "%OPTION%"=="%CHOICE%" goto menu_start
set CHOICES=%CHOICES%%KEY%
set /a "OPTION+=1"

set LABEL=salir
set TEXT=Salir
set KEY=s
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" echo OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" goto %LABEL%
if "%OPTION%"=="%CHOICE%" goto menu_start
set CHOICES=%CHOICES%%KEY%
set /a "OPTION+=1"


rem ########################################################

echo .
choice /C %CHOICES%
set CHOICE=%errorlevel%
set OPTION=1
goto menu


rem ########################################################
rem 
rem Subcomandos externos: se ejecutan en procesos separados
rem 
rem ########################################################

:subcomandos
echo . -------------------------------------------
echo .    Menu jdynamics . Subcomando %1
echo . -------------------------------------------

rem Ejecucion del subcomando
goto %1

echo .
echo ERROR. Subcomando no reconocido %1
echo . 
pause
goto end

rem ########################################################
rem Subcomandos
rem ########################################################


:explorer
timeout %3
start firefox.exe %2
exit 0


:compilarLimpio
call mvn  clean install
goto end

:ejecutar
start menu.bat explorer http://localhost:8080/miapp/ 35
call mvn clean install jetty:run
goto end

:salir
echo .
echo Saliendo de Menu.bat
echo.
goto end


rem ########################################################

:end
pause
exit