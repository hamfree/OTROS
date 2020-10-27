@echo off
chcp 1252
rem ------------------------------------------------------------------------------------
rem Script de Instalacion/Desinstalacion de Servicio NT
rem
rem Opciones
rem install                 Instala el servicio usando valores por defecto.
rem remove                  Elimina el servicio del sistema.
rem
rem service_name (opcional) El nombre a usar para el servicio. Si no es especificado, 
rem                         se usa Tomcat9 como nombre del servicio.
rem 
rem --rename     (opcional) Renombra tomcat9.exe y tomcat9w.exe para que coincida
rem                         con el nombre del servicio alternativo.
rem 
rem username     (opcional) El nombre del usuario del SO a utilizar para 
rem                         instalar/eliminar el servicio (no el nombre del usuario del
rem                         SO con el que el servicio se ejecutara). Si no se 
rem                         especifica, se utiliza el usuario actual.
rem ------------------------------------------------------------------------------------

setlocal

set "SELF=%~dp0%service.bat"

set DEFAULT_SERVICE_NAME=Tomcat9
set SERVICE_NAME=%DEFAULT_SERVICE_NAME%


set "CURRENT_DIR=%cd%"

rem Analiza los argumentos
if "x%1x" == "xx" goto displayUsage
set SERVICE_CMD=%1
shift
if "x%1x" == "xx" goto checkEnv
:checkUser
if "x%1x" == "x/userx" goto runAsUser
if "x%1x" == "x--userx" goto runAsUser
set SERVICE_NAME=%1
shift
if "x%1x" == "xx" goto checkEnv
if "x%1x" == "x--renamex" (
    set RENAME=%1
    shift
)
if "x%1x" == "xx" goto checkEnv
goto checkUser
:runAsUser
shift
if "x%1x" == "xx" goto displayUsage
set SERVICE_USER=%1
shift
runas /env /savecred /user:%SERVICE_USER% "%COMSPEC% /K \"%SELF%\" %SERVICE_CMD% %SERVICE_NAME%"
exit /b 0

rem Comprueba el entorno
:checkEnv

rem Averigua CATALINA_HOME si no esta definida
if not "%CATALINA_HOME%" == "" goto gotHome
set "CATALINA_HOME=%cd%"
if exist "%CATALINA_HOME%\bin\%DEFAULT_SERVICE_NAME%.exe" goto gotHome
if exist "%CATALINA_HOME%\bin\%SERVICE_NAME%.exe" goto gotHome
rem Se cambia al directorio de más arriba
cd ..
set "CATALINA_HOME=%cd%"
:gotHome
if exist "%CATALINA_HOME%\bin\%DEFAULT_SERVICE_NAME%.exe" (
    set "EXECUTABLE=%CATALINA_HOME%\bin\%DEFAULT_SERVICE_NAME%.exe"
    goto okHome
)
if exist "%CATALINA_HOME%\bin\%SERVICE_NAME%.exe" (
    set "EXECUTABLE=%CATALINA_HOME%\bin\%SERVICE_NAME%.exe"
    goto okHome
)
if "%DEFAULT_SERVICE_NAME%"== "%SERVICE_NAME%" (
    echo El fichero %DEFAULT_SERVICE_NAME%.exe no fue encontrado...
) else (
    echo Ni el fichero %DEFAULT_SERVICE_NAME%.exe ni el fichero %SERVICE_NAME%.exe fueron encontrados...
)
echo O la variable de entorno  CATALINA_HOME no se ha definido correctamente o
echo se ha usado un nombre de servicio incorrecto.
echo Tanto la variable de entorno CATALINA_HOME  como el nombre de servicio correcto
echo son requeridos para ejecutar este programa.
exit /b 1
:okHome
cd "%CURRENT_DIR%"

rem Nos aseguramos de que las variables de entorno de requisitos previos estén establecidas
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


rem Java 9 ya no admite la propiedad del sistema java.endorsed.dirs. 
rem Solo trate de usarlo si JAVA_ENDORSED_DIRS fue establecida o
rem existe CATALINA_HOME/endorsed.
set ENDORSED_PROP=ignore.endorsed.dirs
if "%JAVA_ENDORSED_DIRS%" == "" goto noEndorsedVar
set ENDORSED_PROP=java.endorsed.dirs
goto doneEndorsed
:noEndorsedVar
if not exist "%CATALINA_HOME%\endorsed" goto doneEndorsed
set ENDORSED_PROP=java.endorsed.dirs
:doneEndorsed

rem Procesa el comando solicitado
if /i %SERVICE_CMD% == install goto doInstall
if /i %SERVICE_CMD% == remove goto doRemove
if /i %SERVICE_CMD% == uninstall goto doRemove
echo Parametro desconocido "%SERVICE_CMD%"
:displayUsage
echo.
echo Uso: service.bat install/remove [nombre_servicio [--rename]] [--user nombreusuario]
exit /b 1

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
if exist "%CATALINA_HOME%\bin\%SERVICE_NAME%.exe" (
    rename "%SERVICE_NAME%.exe" "%DEFAULT_SERVICE_NAME%.exe"
    rename "%SERVICE_NAME%w.exe" "%DEFAULT_SERVICE_NAME%w.exe"
)
exit /b 0

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

if exist "%CATALINA_HOME%\bin\%DEFAULT_SERVICE_NAME%.exe" (
    if "x%RENAME%x" == "x--renamex" (
        rename "%DEFAULT_SERVICE_NAME%.exe" "%SERVICE_NAME%.exe"
        rename "%DEFAULT_SERVICE_NAME%w.exe" "%SERVICE_NAME%w.exe"
        set "EXECUTABLE=%CATALINA_HOME%\bin\%SERVICE_NAME%.exe"
    )
)

"%EXECUTABLE%" //IS//%SERVICE_NAME% ^
    --Description "Apache Tomcat 9.0.39 Server - https://tomcat.apache.org/" ^
    --DisplayName "Apache Tomcat 9.0 %SERVICE_NAME%" ^
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
exit /b 1
:installed
echo El servicio '%SERVICE_NAME%' ha sido instalado.
exit /b 0