@echo off
chcp 1252
rem ------------------------------------------------------------------------------------
rem Script de Instalacion/Desinstalacion de Servicio NT
rem
rem Opciones
rem install                Instala el servicio usando Tomcat9 como nombre de servicio.
rem                        El servicio es instalado usando valores por defecto.
rem remove                 Elimina el servicio del Sistema.
rem
rem name        (opcional) Si el segundo argumento está presente será considerado 
rem                        el nombre del nuevo servicio
rem ------------------------------------------------------------------------------------

setlocal

set "SELF=%~dp0%service.bat"
rem Adivina CATALINA_HOME si no está definida
set "CURRENT_DIR=%cd%"
if not "%CATALINA_HOME%" == "" goto gotHome
set "CATALINA_HOME=%cd%"
if exist "%CATALINA_HOME%\bin\tomcat9.exe" goto okHome
rem Se cambia al directorio de más arriba
cd ..
set "CATALINA_HOME=%cd%"
:gotHome
if exist "%CATALINA_HOME%\bin\tomcat9.exe" goto okHome
echo El fichero tomcat9.exe no fue encotrado...
echo La variable de entorno CATALINA_HOME no está definida correctamente.
echo Se necesita esta variable de entorno para ejecutar este programa
goto end
:okHome
rem Nos aseguramos de que las variables de entorno de requesitos previos estén establecidas
if not "%JAVA_HOME%" == "" goto gotJdkHome
if not "%JRE_HOME%" == "" goto gotJreHome
echo Ni la variable de entorno JAVA_HOME ni JRE_HOME están definidas
echo El Servicio tratará de adivinarlas desde el registro.
goto okJavaHome
:gotJreHome
if not exist "%JRE_HOME%\bin\java.exe" goto noJavaHome
goto okJavaHome
:gotJdkHome
if not exist "%JAVA_HOME%\bin\javac.exe" goto noJavaHome
rem Java 9 tiene una estructura diferente de directorios
if exist "%JAVA_HOME%\jre\bin\java.exe" goto preJava9Layout
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
if not "%JRE_HOME%" == "" goto okJavaHome
set "JRE_HOME=%JAVA_HOME%"
goto okJavaHome
:preJava9Layout
if not "%JRE_HOME%" == "" goto okJavaHome
set "JRE_HOME=%JAVA_HOME%\jre"
goto okJavaHome
:noJavaHome
echo La variable de entorno JAVA_HOME no está definida correctamente
echo Se necesita esta variable de entorno para ejecutar este programa
echo NB: JAVA_HOME debería apuntar a un KDJ, no a un EEJ
goto end
:okJavaHome
if not "%CATALINA_BASE%" == "" goto gotBase
set "CATALINA_BASE=%CATALINA_HOME%"
:gotBase

set "EXECUTABLE=%CATALINA_HOME%\bin\tomcat9.exe"

rem Se establece el nombre de servicio predeterminado
set SERVICE_NAME=Tomcat9
set DISPLAYNAME=Apache Tomcat 9.0 %SERVICE_NAME%

rem Java 9 ya no admite la propiedad del sistema java.endorsed.dirs. 
rem Solo trate de usarlo si 
rem existe CATALINA_HOME/endorsed.
set ENDORSED_PROP=ignore.endorsed.dirs
if "%JAVA_ENDORSED_DIRS%" == "" goto noEndorsedVar
set ENDORSED_PROP=java.endorsed.dirs
goto doneEndorsed
:noEndorsedVar
if not exist "%CATALINA_HOME%\endorsed" goto doneEndorsed
set ENDORSED_PROP=java.endorsed.dirs
:doneEndorsed

if "x%1x" == "xx" goto displayUsage
set SERVICE_CMD=%1
shift
if "x%1x" == "xx" goto checkServiceCmd
:checkUser
if "x%1x" == "x/userx" goto runAsUser
if "x%1x" == "x--userx" goto runAsUser
set SERVICE_NAME=%1
set DISPLAYNAME=Apache Tomcat 9.0 %1
shift
if "x%1x" == "xx" goto checkServiceCmd
goto checkUser
:runAsUser
shift
if "x%1x" == "xx" goto displayUsage
set SERVICE_USER=%1
shift
runas /env /savecred /user:%SERVICE_USER% "%COMSPEC% /K \"%SELF%\" %SERVICE_CMD% %SERVICE_NAME%"
goto end
:checkServiceCmd
if /i %SERVICE_CMD% == install goto doInstall
if /i %SERVICE_CMD% == remove goto doRemove
if /i %SERVICE_CMD% == uninstall goto doRemove
echo Unknown parameter "%SERVICE_CMD%"
:displayUsage
echo.
echo Usage: service.bat install/remove [service_name] [/user username]
goto end

:doRemove
rem Elimina el servicio
echo Eliminado el servicio '%SERVICE_NAME%' ...
echo Usando CATALINA_BASE:    "%CATALINA_BASE%"

"%EXECUTABLE%" //DS//%SERVICE_NAME% ^
    --LogPath "%CATALINA_BASE%\logs"
if not errorlevel 1 goto removed
echo Falló la eliminación del servicio '%SERVICE_NAME%'
goto end
:removed
echo El servicio '%SERVICE_NAME%' ha sido eliminado
goto end

:doInstall
rem Instala el servicio
echo Instalando el servicio '%SERVICE_NAME%' ...
echo Usando CATALINA_HOME:    "%CATALINA_HOME%"
echo Usando CATALINA_BASE:    "%CATALINA_BASE%"
echo Usando JAVA_HOME:        "%JAVA_HOME%"
echo Usando JRE_HOME:         "%JRE_HOME%"

rem Se intenta usar la mvj de servidor
set "JVM=%JRE_HOME%\bin\server\jvm.dll"
if exist "%JVM%" goto foundJvm
rem Se intenta usar la mvj de cliente
set "JVM=%JRE_HOME%\bin\client\jvm.dll"
if exist "%JVM%" goto foundJvm
echo Advertencia: No se encontró la jvm.dll 'server' ni 'client' en JRE_HOME.
set JVM=auto
:foundJvm
echo Usando JVM:              "%JVM%"

set "CLASSPATH=%CATALINA_HOME%\bin\bootstrap.jar;%CATALINA_BASE%\bin\tomcat-juli.jar"
if not "%CATALINA_HOME%" == "%CATALINA_BASE%" set "CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\bin\tomcat-juli.jar"

if "%SERVICE_STARTUP_MODE%" == "" set SERVICE_STARTUP_MODE=manual
if "%JvmMs%" == "" set JvmMs=128
if "%JvmMx%" == "" set JvmMx=256

"%EXECUTABLE%" //IS//%SERVICE_NAME% ^
    --Description "Apache Tomcat 9.0.10 Server - http://tomcat.apache.org/" ^
    --DisplayName "%DISPLAYNAME%" ^
    --Install "%EXECUTABLE%" ^
    --LogPath "%CATALINA_BASE%\logs" ^
    --StdOutput auto ^
    --StdError auto ^
    --Classpath "%CLASSPATH%" ^
    --Jvm "%JVM%" ^
    --StartMode jvm ^
    --StopMode jvm ^
    --StartPath "%CATALINA_HOME%" ^
    --StopPath "%CATALINA_HOME%" ^
    --StartClass org.apache.catalina.startup.Bootstrap ^
    --StopClass org.apache.catalina.startup.Bootstrap ^
    --StartParams start ^
    --StopParams stop ^
    --JvmOptions "-Dcatalina.home=%CATALINA_HOME%;-Dcatalina.base=%CATALINA_BASE%;-D%ENDORSED_PROP%=%CATALINA_HOME%\endorsed;-Djava.io.tmpdir=%CATALINA_BASE%\temp;-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager;-Djava.util.logging.config.file=%CATALINA_BASE%\conf\logging.properties;%JvmArgs%" ^
    --JvmOptions9 "--add-opens=java.base/java.lang=ALL-UNNAMED#--add-opens=java.base/java.io=ALL-UNNAMED#--add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED" ^
    --Startup "%SERVICE_STARTUP_MODE%" ^
    --JvmMs "%JvmMs%" ^
    --JvmMx "%JvmMx%"
if not errorlevel 1 goto installed
echo Falló la instalación del servicio '%SERVICE_NAME%'
goto end
:installed
echo El servicio '%SERVICE_NAME%' ha sido instalado.

:end
cd "%CURRENT_DIR%"
