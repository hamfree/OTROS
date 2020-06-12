@echo off
chcp 1252

rem La linea siguiente puede usarse para definir una ruta especifica de JRE o JDK
rem set "JAVA_HOME=C:/JDK"

REM Prefiere un JRE local si encontramos uno en el directorio bin actual
IF EXIST "%~dp0jre" (
  SET "JRE_HOME=%~dp0jre"
) 

REM Prefiere un JDK local si encontramos unos en el directorio bin actual
IF EXIST "%~dp0jdk" (
  SET "JAVA_HOME=%~dp0jdk"
)

@IF DEFINED ECHO  ECHO %ECHO%
@IF "%OS%" == "Windows_NT" setlocal

IF "%OS%" == "Windows_NT" (
  SET "DIRNAME=%~dp0%"
) ELSE (
  SET DIRNAME=.\
)

pushd %DIRNAME%

rem ---------------------------------------------------------------------------------
rem Script de Instalacion/Desinstalacion de Servicio NT
rem
rem Opciones
rem install                Instala el servicio usando TomEE como nombre de servicio.
rem                        El servicio es instalado usando valores por defecto.
rem remove                 Elimina el servicio del Sistema.
rem
rem name        (opcional) Si el segundo argumento está presente será considerado 
rem                        el nombre del nuevo servicio
rem ---------------------------------------------------------------------------------

SET proc=undefined

IF /i %PROCESSOR_ARCHITECTURE% EQU X86 SET "proc=%~dp0TomEE.x86.exe"
IF /i %PROCESSOR_ARCHITECTURE% EQU AMD64 SET "proc=%~dp0TomEE.amd64.exe"
IF /i %PROCESSOR_ARCHITECTURE% EQU IA64 SET "proc=%~dp0TomEE.ia64.exe"

IF /i "%proc%" EQU undefined (
	ECHO Error al determinar la arquitectura del sistema operativo
	GOTO end
)

set "SELF=%~dp0%service.bat"
rem Adivina CATALINA_HOME si no está definida
set "CURRENT_DIR=%cd%"
if DEFINED CATALINA_HOME goto gotHome
set "CATALINA_HOME=%cd%"
if exist "%CATALINA_HOME%\bin\service.bat" goto okHome
rem Se cambia al directorio de más arriba
cd ..
set "CATALINA_HOME=%cd%"
:gotHome
if exist "%CATALINA_HOME%\bin\service.bat" goto okHome
echo El servicio ejecutable no fue encontrado...
echo La variable de entorno CATALINA_HOME no está definida correctamente.
echo Se necesita esta variable de entorno para ejecutar este programa
goto end
:okHome
rem Nos aseguramos de que las variables de entorno de requesitos previos estén establecidas
if DEFINED JAVA_HOME goto gotJdkHome
if DEFINED JRE_HOME goto gotJreHome
echo Ni la variable de entorno JAVA_HOME ni JRE_HOME están definidas
echo El Servicio tratará de adivinarlas desde el registro.
goto okJavaHome
:gotJreHome
if not exist "%JRE_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JRE_HOME%\bin\javaw.exe" goto noJavaHome
goto okJavaHome
:gotJdkHome
if not exist "%JAVA_HOME%\jre\bin\java.exe" goto noJavaHome
if not exist "%JAVA_HOME%\jre\bin\javaw.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javac.exe" goto noJavaHome
if not "%JRE_HOME%" == "" goto okJavaHome
set "JRE_HOME=%JAVA_HOME%\jre"
goto okJavaHome
:noJavaHome
echo La variable de entorno JAVA_HOME no está definida correctamente
echo Se necesita esta variable de entorno para ejecutar este programa
echo NB: JAVA_HOME debería apuntar a un KDJ, no a un EEJ
goto end
:okJavaHome
if DEFINED CATALINA_BASE goto gotBase
set "CATALINA_BASE=%CATALINA_HOME%"
:gotBase

set "EXECUTABLE=%proc%"

rem Se establece el nombre de servicio predeterminado (Si usted cambia esto entonces renombre tambien TomEE.exe al mismo nombre)
set SERVICE_NAME=TomEE
set PR_DISPLAYNAME=Apache TomEE

if "x%1x" == "xx" goto displayUsage
set SERVICE_CMD=%1
shift
if "x%1x" == "xx" goto checkServiceCmd
:checkUser
if "x%1x" == "x/userx" goto runAsUser
if "x%1x" == "x--userx" goto runAsUser
set SERVICE_NAME=%1
set PR_DISPLAYNAME=Apache TomEE (%1)
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
echo Parametro desconocido "%1"
:displayUsage
echo.
echo Uso: service.bat install/remove [nombre_servicio] [/user nombreusuario]
goto end

:doRemove
rem Elimina el servicio
"%EXECUTABLE%" //DS//%SERVICE_NAME%
if not errorlevel 1 goto removed
echo Se fallo al eliminar el servicio '%SERVICE_NAME%'
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

rem Usa las variables de entorno como un ejemplo
rem cada opcion de linea de comando se prefija con PR_

set "PR_DESCRIPTION=Apache TomEE - http://tomee.apache.org/"
set "PR_INSTALL=%EXECUTABLE%"
set "PR_LOGPATH=%CATALINA_BASE%\logs"
set "PR_CLASSPATH=%CATALINA_HOME%\bin\bootstrap.jar;%CATALINA_BASE%\bin\tomcat-juli.jar;%CATALINA_HOME%\bin\tomcat-juli.jar"
rem Establece la jvm de servidor desde JAVA_HOME
set "PR_JVM=%JRE_HOME%\bin\server\jvm.dll"
if exist "%PR_JVM%" goto foundJvm
rem Establece la jvm de cliente desde JAVA_HOME
set "PR_JVM=%JRE_HOME%\bin\client\jvm.dll"
if exist "%PR_JVM%" goto foundJvm
set PR_JVM=auto
:foundJvm
echo Usando JVM:              "%PR_JVM%"

"%EXECUTABLE%" //IS//%SERVICE_NAME% ^
    --DisplayName=%SERVICE_NAME% ^
    --StartClass org.apache.catalina.startup.Bootstrap ^
    --StopClass org.apache.catalina.startup.Bootstrap ^
    --StartParams start ^
    --StopParams stop ^
    --Startup auto ^
    --JvmMs=512 ^
    --JvmMx=1024 ^
    --JvmSs=2048 ^
    --StartMode jvm ^
    --StopMode jvm ^
    --LogLevel Info ^
    --LogPrefix TomEE
    
echo Instalado, ahora configuraremos TomEE
    
if not errorlevel 1 goto installed
echo Fallo la instalacion del servicio '%SERVICE_NAME%'
goto end

:installed
rem Limpia las variables de entorno. Ya no son necesarias.
set PR_DISPLAYNAME=
set PR_DESCRIPTION=
set PR_INSTALL=
set PR_LOGPATH=
set PR_CLASSPATH=
set PR_JVM=

rem Establece parametros extra
"%EXECUTABLE%" //US//%SERVICE_NAME% ^
	++JvmOptions "-javaagent:%CATALINA_HOME%\lib\openejb-javaagent.jar;-Dcatalina.base=%CATALINA_BASE%;-Dcatalina.home=%CATALINA_HOME%;-Djava.endorsed.dirs=%CATALINA_HOME%\endorsed"

rem Mas parametros extra
set "PR_LOGPATH=%CATALINA_BASE%\logs"
set PR_STDOUTPUT=auto
set PR_STDERROR=auto

rem antes de que esta opcion fuera agregada: "++JvmOptions=-Djava.library.path="%CATALINA_BASE%\bin" ^"
rem El inconveniente era que impedia que se cargara la biblioteca nativa personalizada incluso si se agregaba a Path
"%EXECUTABLE%" //US//%SERVICE_NAME% ^
	++JvmOptions "-Djava.io.tmpdir=%CATALINA_BASE%\temp;-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager;-Djava.util.logging.config.file=%CATALINA_BASE%\conf\logging.properties;-Djava.awt.headless=true;-XX:+UseParallelGC;-XX:MaxPermSize=256M"

echo El servicio  '%SERVICE_NAME%' ha sido instalado.

:end
cd "%CURRENT_DIR%"
