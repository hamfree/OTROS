@echo off
echo ########################################################
echo .
echo . menu.bat Menu para ejecución sencilla de tareas
echo . (C) Cristobal Gonzalez Almiron 2014
echo . (modificado por H4mfr33 para ELSUPER)
echo . 
echo ########################################################
echo .
rem obtener la unidad actual conservando la ruta actual
IF NOT DEFINED JDYNAMICS_PATH SET JDYNAMICS_PATH=%CD%
cd \
set ROOT_DRIVE=%CD%
cd /D %JDYNAMICS_PATH%

rem Variables de entorno por defecto

IF NOT DEFINED JAVA_HOME SET JAVA_HOME=F:\des\bin\jdk
IF NOT DEFINED MVN_PATH set PATH=%ROOT_DRIVE%%MVN_HOME%\bin;%PATH%
IF NOT DEFINED MVN_PATH SET MVN_PATH=%ROOT_DRIVE%%MVN_HOME%\bin
IF NOT DEFINED MYSQL_PATH SET MYSQL_PATH=F:\des\bin\mysql\bin
IF NOT DEFINED TARGET SET TARGET=%JDYNAMICS_PATH%\target


rem Opciones de Maven
rem set MAVEN_OPTS="-Dfile.encoding=UTF-8" 
IF NOT DEFINED MAVEN_SETTINGS set MAVEN_SETTINGS=-s%JDYNAMICS_PATH%\settings.xml
IF NOT DEFINED MAVEN_OFFLINE SET MAVEN_OFFLINE=-o
rem - Por defecto aumentamos la memoria para evitar problemas de PermGen
rem - Si es necesario aumentar o midificar estas opciones a medida que el 
rem   proyecto se complique
if not defined MAVEN_OPTS set MAVEN_OPTS="-XX:MaxPermSize=1024m"


rem ########################################################

rem #### Subcommands execution
IF NOT "%1"=="" GOTO subcomandos

rem ########################################################

echo Variables iniciales:
echo ROOT_DRIVE=%ROOT_DRIVE%
echo CD=%CD%
echo JAVA_HOME=%JAVA_HOME%
echo JDYNAMICS_PATH=%JDYNAMICS_PATH%
echo MAVEN_OPTS=%MAVEN_OPTS%
echo MAVEN_SETTINGS=%MAVEN_SETTINGS%
echo MAVEN_OFFLINE=%MAVEN_OFFLINE%
echo MYSQL_PATH=%MYSQL_PATH%

:menu_start
rem Variables actuales
rem Aña
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
rem Opciones de menú
rem ########################################################



set LABEL=compilarLimpio
set TEXT=Compilar Jdynbamics completo limpiando .m2/repositories/es/com/jdynamics
set KEY=0
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" start %CD%\supermenu.bat %LABEL%
if "%OPTION%"=="%CHOICE%" goto menu_start
set CHOICES=%CHOICES%%KEY%
set /a "OPTION+=1"

set LABEL=compilarcore
set TEXT=Compilar Jdynbamics Main y Core
set KEY=1
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" echo OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" start %CD%\supermenu.bat %LABEL%
if "%OPTION%"=="%CHOICE%" goto menu_start
set CHOICES=%CHOICES%%KEY%
set /a "OPTION+=1"


set LABEL=compilarcoremgr
set TEXT=Compilar Jdynbamics Core Manager
set KEY=2
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" echo OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" start %CD%\supermenu.bat %LABEL%
if "%OPTION%"=="%CHOICE%" goto menu_start
set CHOICES=%CHOICES%%KEY%
set /a "OPTION+=1"

echo .

set LABEL=testjdynamicsbuilder
set TEXT=Tests Unitarios Jdynbamics Builder
set KEY=t
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" echo OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" start %CD%\supermenu.bat %LABEL%
if "%OPTION%"=="%CHOICE%" goto menu_start
set CHOICES=%CHOICES%%KEY%
set /a "OPTION+=1"


echo .

set LABEL=compilarjdadmin
set TEXT=Compilar Jdynbamics JDAdmin para JSF2
set KEY=j
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" echo OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" start %CD%\supermenu.bat %LABEL%
if "%OPTION%"=="%CHOICE%" goto menu_start
set CHOICES=%CHOICES%%KEY%
set /a "OPTION+=1"

echo .

set LABEL=compilarwebcontrol2
set TEXT=Compilar Jdynbamics Webcontrol V2 para JSF2
set KEY=w
if "%CHOICE%"=="" echo . %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" echo OPCION ELEGIDA:  %KEY%. %TEXT%  
if "%OPTION%"=="%CHOICE%" start %CD%\supermenu.bat %LABEL%
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
echo MAVEN_OPTS=%MAVEN_OPTS%

rem Ejecución del subcomando
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
start iexplore.exe %2
exit 0

:compilarLimpio
rmdir /s /q %USERPROFILE%\.m2\repository\es\com\jdynamics
if "%errorlevel%"=="0" echo Repositorio maven local borrado
call mvn  clean install
goto end


:compilarcore
cd jdynamics-main
call mvn clean install
cd ..\jdynamics-core
call mvn clean install
goto end

:compilarcoremgr
cd jdynamics-coremgr
call mvn clean install
goto end


:compilarwebcontrol
start menu.bat explorer http://localhost:8080/webcontrol/ 35
cd webcontrol
call mvn clean install dependency:analyze jetty:run
goto end

:compilarwebcontrol2
start menu.bat explorer http://localhost:8080/jdynamics/webcontrol/main.xhtml 35
cd webcontrol2
call mvn clean install dependency:analyze jetty:run
goto end

:compilarjdadmin
start menu.bat explorer http://localhost:8080/jdynamics/jdadmin/main.xhtml 35
cd jdadmin
call mvn clean install dependency:analyze-report dependency:analyze jetty:run
goto end


:testjdynamicsbuilder
cd jdynamics-builder
call mvn clean test dependency:analyze -DskipTests=false -Dtest=PageBuilderTest#testPageBuilder_children
goto end



:end
pause
exit