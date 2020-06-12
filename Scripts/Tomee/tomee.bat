@echo off

setlocal

set port=8080

rem Averigua CATALINA_HOME si no esta definida
set "CURRENT_DIR=%cd%"
if DEFINED CATALINA_HOME goto gotHome
set "CATALINA_HOME=%CURRENT_DIR%"
if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
cd ..
set "CATALINA_HOME=%cd%"
cd "%CURRENT_DIR%"
:gotHome

if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
echo La variable de entorno CATALINA_HOME no esta definida correctamente
echo Se necesita esta variable de entorno para ejecutar este programa
goto end
:okHome

rem Copia CATALINA_BASE de CATALINA_HOME si no esta definida
if DEFINED CATALINA_BASE goto gotBase
set "CATALINA_BASE=%CATALINA_HOME%"
:gotBase

rem Se asegura de que cualquier variable CLASSPATH definida por el usuario no se use en el arranque,
rem pero se permite que se especifique en setenv.bat, en el caso raro de que se necesite.
set CLASSPATH=

if not exist "%CATALINA_BASE%\bin\tomcat-juli.jar" goto juliClasspathHome
set "CLASSPATH=%CLASSPATH%;%CATALINA_BASE%\bin\tomcat-juli.jar"
goto juliClasspathDone
:juliClasspathHome
set "CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\bin\tomcat-juli.jar"
:juliClasspathDone

rem Get standard Java environment variables
if exist "%CATALINA_HOME%\bin\setclasspath.bat" goto okSetclasspath
echo No puedo encontrar "%CATALINA_HOME%\bin\setclasspath.bat"
echo Se necesita este fichero para ejecutar este programa
goto end
:okSetclasspath
call "%CATALINA_HOME%\bin\setclasspath.bat" %1
if errorlevel 1 goto end

if DEFINED CATALINA_TMPDIR goto gotTmpdir
set "CATALINA_TMPDIR=%CATALINA_BASE%\temp"
:gotTmpdir

if not exist %CATALINA_BASE% goto :libClasspathHome
set CLASSPATH=%CLASSPATH%;%CATALINA_BASE%\lib\*
if "%CATALINA_BASE%" equ %CATALINA_HOME% goto :libClasspathDone
:libClasspathHome
if not exist %CATALINA_HOME% goto :libClasspathDone
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\lib\*
:libClasspathDone

set DEBUG=
set "args=%*"
if ""%1"" == ""deploy"" goto doDeploy
if ""%1"" == ""undeploy"" goto doUndeploy
if ""%1"" == ""start"" goto unsupportedCmd
if ""%1"" == ""stop"" goto unsupportedCmd
if not ""%1"" == ""debug"" goto doExec
set DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005
set "args=%*"
set "args=%args:* =%"
goto doExec

:doDeploy
:doUndeploy
%_RUNJAVA% -Djava.io.tmpdir="%CATALINA_TMPDIR%" org.apache.openejb.cli.Bootstrap %1 -s http://localhost:%port%/tomee/ejb %2
goto end

:unsupportedCmd
echo Los comandos start/stop no son compatibles con tomee.bat, por favor use catalina.bat/startup.bat/shutdown.bat
goto end

:doExec
%_RUNJAVA% %DEBUG% "-Dopenejb.base=%CATALINA_BASE%" "-Dopenejb.home=%CATALINA_HOME%" "-Djava.io.tmpdir=%CATALINA_TMPDIR%" org.apache.openejb.cli.Bootstrap %args%
goto end

:end

