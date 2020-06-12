@echo off
chcp 1252

rem ---------------------------------------------------------------------------
rem Script envoltorio para las herramientas de línea de comandos
rem
rem Variables de Entorno con los prerequisitos
rem
rem   CATALINA_HOME   Puede apuntar a su directorio "build" de Catalina.
rem
rem   TOOL_OPTS       (Opcional) Opciones del tiempo de ejecución de Java.
rem
rem   JAVA_HOME       Debe apuntar a su instalación del Kit de Desarrollo de 
rem                   Java. Usar JRE_HOME en su lugar también funciona.
rem
rem   JRE_HOME        Debe apuntar a su instalación de Tiempo de Ejecución de 
rem                   Java. El valor por defecto será JAVA_HOME si está vacía. 
rem                   Si se fijan tanto JRE_HOME como JAVA_HOME, se usará 
rem                   JRE_HOME.
rem
rem   JAVA_OPTS       (Opcional) Opciones del tiempo de ejecución de Java.
rem
rem   JAVA_ENDORSED_DIRS (Opcional) Listas de directorios separados por  
rem                   puntos y comas conteniendo algunos jars para permitir
rem                   el reemplazo de las IPAs creadas fuera del JCP (p.e. 
rem                   DOM y SAX del W3C). También se puede usar para actualizar
rem                   la implementación del analizador XML. Esto es solo 
rem                   compatible con Java ^<= 8. El valor predeterminado es 
rem                   $CATALINA_HOME/endorsed.
rem ---------------------------------------------------------------------------

setlocal

rem Adivina CATALINA_HOME si no está definida
set "CURRENT_DIR=%cd%"
if not "%CATALINA_HOME%" == "" goto gotHome
set "CATALINA_HOME=%CURRENT_DIR%"
if exist "%CATALINA_HOME%\bin\tool-wrapper.bat" goto okHome
cd ..
set "CATALINA_HOME=%cd%"
cd "%CURRENT_DIR%"
:gotHome
if exist "%CATALINA_HOME%\bin\tool-wrapper.bat" goto okHome
echo La variable de entorno CATALINA_HOME no está definida correctamente.
echo Se necesita esta variable de entorno para ejecutar este programa
goto end
:okHome

rem Se asegura de que cualquier variable CLASSPATH definida por el usuario no es 
rem utilizada en el arranque, pero se las permite a ser especificadas en setenv.bat, 
rem en el caso raro de que sean necesitadas.
set CLASSPATH=

rem Se obtienen las variables de entorno estándar
if exist "%CATALINA_HOME%\bin\setenv.bat" call "%CATALINA_HOME%\bin\setenv.bat"

rem Se obtienen las variables de entorno estándar de Java
if exist "%CATALINA_HOME%\bin\setclasspath.bat" goto okSetclasspath
echo No se puede encontrar "%CATALINA_HOME%\bin\setclasspath.bat"
echo Este fichero es necesario para ejecutar este programa
goto end
:okSetclasspath
call "%CATALINA_HOME%\bin\setclasspath.bat" %1
if errorlevel 1 goto end

rem Se agrega un fichero jar extra al CLASSPATH
rem Dese cuenta de que no hay comillas ya que no queremos introducir comillas
rem al azar en el CLASSPATH
if "%CLASSPATH%" == "" goto emptyClasspath
set "CLASSPATH=%CLASSPATH%;"
:emptyClasspath
set "CLASSPATH=%CLASSPATH%%CATALINA_HOME%\bin\bootstrap.jar;%CATALINA_HOME%\bin\tomcat-juli.jar;%CATALINA_HOME%\lib\servlet-api.jar;%CATALINA_HOME%\lib\tomcat-util.jar"

set JAVA_OPTS=%JAVA_OPTS% -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager

rem Java 9 ya no soporta la propiedad del sistema 
rem java.endorsed.dirs. Intente usarla sólo si 
rem JAVA_ENDORSED_DIRS fue establecida explícitamente
rem o exista CATALINA_HOME/endorsed.
set ENDORSED_PROP=ignore.endorsed.dirs
if "%JAVA_ENDORSED_DIRS%" == "" goto noEndorsedVar
set ENDORSED_PROP=java.endorsed.dirs
goto doneEndorsed
:noEndorsedVar
if not exist "%CATALINA_HOME%\endorsed" goto doneEndorsed
set ENDORSED_PROP=java.endorsed.dirs
:doneEndorsed

rem Se obtienen los argumentos restantes de la línea de comandos sin modificar y se guardan
set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

%_RUNJAVA% %JAVA_OPTS% %TOOL_OPTS% -D%ENDORSED_PROP%="%JAVA_ENDORSED_DIRS%" -classpath "%CLASSPATH%" -Dcatalina.home="%CATALINA_HOME%" org.apache.catalina.startup.Tool %CMD_LINE_ARGS%

:end
