@echo off
rem ---------------------------------------------------------------------------
rem Establezca JAVA_HOME o JRE_HOME si no estan ya establecidas, asegurese de 
rem que cualquier configuracion suministrada es valida y consistente con las 
rem opciones de inicio seleccionadas y configure el directorio aprobado.
rem ---------------------------------------------------------------------------

rem Asegurese de que las variables de entorno de requisitos previos esten establecidas

rem set JAVA_HOME=C:\des\bin\jdk11
rem set JRE_HOME=

echo JRE_HOME = %JRE_HOME%
echo JAVA_HOME = %JAVA_HOME%

rem En el modo de depuracion necesitamos un KDJ real (JAVA_HOME)
if ""%1"" == ""debug"" goto needJavaHome

rem De lo contrario, JRE o JDK estan bien
if not "%JRE_HOME%" == "" goto gotJreHome
if not "%JAVA_HOME%" == "" goto gotJavaHome
echo Ni la variable de entorno JAVA_HOME ni JRE_HOME estan definidas
echo Se necesita al menos una de estas variables de entorno para ejecutar este programa
goto exit

:needJavaHome
rem Comprueba si tenemos un JDK utilizable
if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\jdb.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javac.exe" goto noJavaHome
set "JRE_HOME=%JAVA_HOME%"
goto okJava

:noJavaHome
echo La variable de entorno JAVA_HOME no esta definida correctamente.
echo Es necesitada para ejecutar este programa en modo depuracion.
echo NB: JAVA_HOME deberia apuntar a un JDK, no a un JRE.
goto exit

:gotJavaHome
rem No se proporciona JRE, use JAVA_HOME como JRE_HOME
set "JRE_HOME=%JAVA_HOME%"

:gotJreHome
rem Comprueba si tenemos un JRE utilizable
if not exist "%JRE_HOME%\bin\java.exe" goto noJreHome
goto okJava

:noJreHome
rem Necesitaba al menos un JRE
echo La variable de entorno JRE_HOME no esta definida correctamente
echo Esta variable de entorno es necesaria para ejecutar este programa.
goto exit

:okJava
rem No anule el directorio respaldado si el usuario lo ha configurado previamente
if not "%JAVA_ENDORSED_DIRS%" == "" goto gotEndorseddir
rem Java 9 ya no admite la propiedad del sistema java.endorsed.dirs. 
rem Solo trate de usarlo si 
rem existe CATALINA_HOME/endorsed.
if not exist "%CATALINA_HOME%\endorsed" goto gotEndorseddir
set "JAVA_ENDORSED_DIRS=%CATALINA_HOME%\endorsed"
:gotEndorseddir

rem No anule _RUNJAVA si el usuario lo ha configurado previamente
if not "%_RUNJAVA%" == "" goto gotRunJava
rem Establece el comando estandar para invocar Java.
rem Tambien tenga en cuenta las comillas ya que JRE_HOME puede contener espacios.
set _RUNJAVA="%JRE_HOME%\bin\java.exe"
:gotRunJava

rem No anule _RUNJDB si el usuario lo ha configurado previamente
rem Tambien tenga en cuenta las comillas ya que JAVA_HOME puede contener espacios.
if not "%_RUNJDB%" == "" goto gotRunJdb
set _RUNJDB="%JAVA_HOME%\bin\jdb.exe"
:gotRunJdb

goto end

:exit
exit /b 1

:end
exit /b 0
