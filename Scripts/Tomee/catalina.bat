@echo off
@chcp 1252 >nul
rem ---------------------------------------------------------------------------
rem Script de inicio/parada para el Servidor CATALINA
rem
rem Prerrequisitos variables de entorno
rem
rem   No establezca las variables en este script. En cambio, pongalos en un 
rem   script setenv.bat en CATALINA_BASE/bin para mantener sus 
rem   personalizaciones separadas.
rem
rem   CUANDO EJECUTE TOMCAT COMO UN SERVICIO DE WINDOWS:
rem   Note que las variables de entorno que afectan al comportamiento de este 
rem   script no tendran ningun efecto en los Servicios de Windows. Como tal, 
rem   cualesquiera personalizacion local hecha en el script 
rem   CATALINA_BASE/bin/setenv.bat tampoco tendra efecto sobre Tomcat cuando 
rem   sea lanzado como un Servicio de Windows.
rem   La configuracion que controla los Servicios de windows es almacenada en 
rem   el Registro de Windows, y es mas convenientemente mantenida usando la 
rem   utilidad de mantenimiento "tomcatXw.exe", donde "X" es la version 
rem   mayor de Tomcat que esta ejecutando.
rem   
rem   CATALINA_HOME   Puede apuntar al directorio "build" de Catalina.
rem
rem   CATALINA_BASE   (Opcional) El directorio Base para resolver porciones 
rem                   dinamicas de la instalacion de Catalina. Si no esta 
rem                   presente, resuelve al mismo directorio al que apunta 
rem                   CATALINA_HOME.
rem
rem   CATALINA_OPTS   (Opcional) Opciones del tiempo de ejecucion de Java 
rem                   usadas cuando se ejecuta el comando "start", "run" o 
rem                   "debug".
rem                   Incluya aqui y no en JAVA_OPTS todas las opciones, 
rem                   que deberian ser solo usadas por el propio Tomcat, 
rem                   no por el proceso de parada, el comando version, etc.
rem                   Ejemplo son el tamaño del monton, trazas del GC, 
rem                   puertos de JMX, etc.
rem
rem   CATALINA_TMPDIR (Opcional) Ubicacion de la ruta del directorio del 
rem                   directorio temporal que la JVM debe usar 
rem                   (java.io.tmpdir). Por defecto es %CATALINA_BASE%\temp.
rem 
rem   JAVA_HOME       Debe apuntar a su instalacion del Kit de Desarrollo 
rem                   de Java. Es requerido para ejecutar con el argumento
rem                   "debug".
rem
rem   JRE_HOME        Debe apuntar a sun instalacion de Tiempo de Ejecucion  
rem                   de Java. El valor por defecto es JAVA_HOME si esta 
rem                   vacia. Si se establecen tanto JRE_HOME como JAVA_HOME,
rem                   sera usado JRE_HOME.
rem
rem   JAVA_OPTS       (Opcional) Las opciones del tiempo de ejecucion de 
rem                   Java usadas cuando cualquier comando es ejecutado.
rem                   Incluya aqui y no en CATALINA_OPTS todas las opciones, 
rem                   que seran usadas por Tomcat y tambien por el proceso 
rem                   de parada, el comando version, etc.
rem                   La mayoria de las opciones iran en CATALINA_OPTS.
rem 
rem   JAVA_ENDORSED_DIRS (Opcional) Listas de directorios separados por  
rem                   puntos y comas conteniendo algunos jars para permitir
rem                   el reemplazo de las IPAs creadas fuera del JCP (p.e. 
rem                   DOM y SAX del W3C). Tambien se puede usar para actualizar
rem                   la implementacion del analizador XML. Esto es solo 
rem                   compatible con Java ^<= 8. El valor predeterminado es 
rem                   $CATALINA_HOME/endorsed.
rem   
rem   JPDA_TRANSPORT  (Opcional) El transporte JPDA usado cuando se ejecute 
rem                   el comando "jpda start". El valor por defecto es 
rem                   "dt_socket".
rem   JPDA_ADDRESS    (Opcional) Las opciones de tiempo de ejecucion de Java
rem                   usadas cuando se ejecute el comando "jpda start". El 
rem                   valor por defecto es localhost:8000.
rem 
rem   JPDA_SUSPEND    (Opcional) Las opciones de tiempo de ejecucion de Java 
rem                   usadas cuando se ejecuta el comando "jpda start". 
rem                   Especifica si la MVJ debe suspender la ejecucion 
rem                   inmediatamente despues del arranque. El valor por 
rem                   defecto es "n".
rem
rem   JPDA_OPTS       (Opcional) Las opciones de tiempo de ejecucion de Java 
rem                   usadas cuando se ejecuta el comando "jpda start". Si se 
rem                   usa, JPDA_TRANSPORT, JPDA_ADDRESS y JPDA_SUSPEND son 
rem                   ignoradas. Asi, todas las opciones requeridas de jpda 
rem                   DEBEN ser especificadas. Los valores por defecto son:
rem
rem                   -agentlib:jdwp=transport=%JPDA_TRANSPORT%,
rem                       address=%JPDA_ADDRESS%,server=y,suspend=%JPDA_SUSPEND%
rem
rem   JSSE_OPTS       (Opcional) Las opciones de tiempo de ejecucion de Java    
rem                   usadas para controlar la implementacion TLS cuando se 
rem                   usa JSSE. El valor por defecto es:
rem                   "-Djdk.tls.ephemeralDHKeySize=2048"
rem
rem   LOGGING_CONFIG  (Opcional) Sobreescribe el fichero de configuracion de   
rem                   las trazas de Tomcat.
rem                   Ejemplo (todo en una linea)
rem                   set LOGGING_CONFIG="-Djava.util.logging.config.file=%CATALINA_BASE%\conf\logging.properties"
rem 
rem   LOGGING_MANAGER (Opcional) Sobreescribe el gestor de trazas de Tomcat   
rem                   Ejemplo (todo en una linea)
rem                   set LOGGING_MANAGER="-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager"
rem
rem   TITLE           (Opcional) Especifica el t´tiulo de la ventana de Tomcat. 
rem                   El valor por defecto de TITLE es Tomcat si no se ha 
rem                   especificado.
rem                   Ejemplo (todo en una linea)
rem                   set TITLE=Tomcat.Cluster#1.Server#1 [%DATE% %TIME%]
rem                   
rem ---------------------------------------------------------------------------

setlocal

rem Suprimir Terminar trabajo por lotes en CTRL + C
if not ""%1"" == ""run"" goto mainEntry
if "%TEMP%" == "" goto mainEntry
if exist "%TEMP%\%~nx0.run" goto mainEntry
echo Y>"%TEMP%\%~nx0.run"
if not exist "%TEMP%\%~nx0.run" goto mainEntry
echo Y>"%TEMP%\%~nx0.Y"
call "%~f0" %* <"%TEMP%\%~nx0.Y"
rem Usa el nivel de error proporcionado
set RETVAL=%ERRORLEVEL%
del /Q "%TEMP%\%~nx0.Y" >NUL 2>&1
exit /B %RETVAL%
:mainEntry
del /Q "%TEMP%\%~nx0.run" >NUL 2>&1

rem Adivina  CATALINA_HOME si no esta definido
set "CURRENT_DIR=%cd%"
if not "%CATALINA_HOME%" == "" goto gotHome
set "CATALINA_HOME=%CURRENT_DIR%"
if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
cd ..
set "CATALINA_HOME=%cd%"
cd "%CURRENT_DIR%"
:gotHome

if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
echo La variable de entorno CATALINA_HOME no esta definida correctamente
echo Esta variable de entorno es necesaria para ejecutar este programa.
goto end
:okHome

rem Copia CATALINA_BASE de CATALINA_HOME si no esta definida
if not "%CATALINA_BASE%" == "" goto gotBase
set "CATALINA_BASE=%CATALINA_HOME%"
:gotBase

rem Se asegura de que ni CATALINA_HOME ni CATALINA_BASE contengan un punto y coma, 
rem ya que se utiliza como separador en el classpath y Java no proporciona 
rem ningun mecanismo para escapar si el mismo caracter aparece en el camino. 
rem Se Verifica esto reemplazando todas las apariciones de ';' con '' y 
rem comprobando que ni CATALINA_HOME ni CATALINA_BASE han cambiado
if "%CATALINA_HOME%" == "%CATALINA_HOME:;=%" goto homeNoSemicolon
echo Usando CATALINA_HOME:   "%CATALINA_HOME%"
echo Incapaz de iniciar ya que CATALINA_HOME contiene un caracter de punto y coma (;)
goto end
:homeNoSemicolon

if "%CATALINA_BASE%" == "%CATALINA_BASE:;=%" goto baseNoSemicolon
echo Usando CATALINA_BASE:   "%CATALINA_BASE%"
echo Incapaz de iniciar ya que CATALINA_BASE contiene un caracter de punto y coma (;)
goto end
:baseNoSemicolon

rem Se asegura de que cualquier variable CLASSPATH definida por el usuario no es 
rem utilizada en el arranque, pero se las permite a ser especificadas en setenv.bat, 
rem en el caso raro de que sean necesitadas.
set CLASSPATH=

rem Se obtienen las variables de entorno estandar
if not exist "%CATALINA_BASE%\bin\setenv.bat" goto checkSetenvHome
call "%CATALINA_BASE%\bin\setenv.bat"
goto setenvDone
:checkSetenvHome
if exist "%CATALINA_HOME%\bin\setenv.bat" call "%CATALINA_HOME%\bin\setenv.bat"
:setenvDone

rem Se obtienen las variables de entorno estandar de Java
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
set "CLASSPATH=%CLASSPATH%%CATALINA_HOME%\bin\bootstrap.jar"

if not "%CATALINA_TMPDIR%" == "" goto gotTmpdir
set "CATALINA_TMPDIR=%CATALINA_BASE%\temp"
:gotTmpdir

rem Agrega tomcat-juli.jar al CLASSPATH
rem tomcat-juli.jar puede ser sobreescrito por instancia
if not exist "%CATALINA_BASE%\bin\tomcat-juli.jar" goto juliClasspathHome
set "CLASSPATH=%CLASSPATH%;%CATALINA_BASE%\bin\tomcat-juli.jar"
goto juliClasspathDone
:juliClasspathHome
set "CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\bin\tomcat-juli.jar"
:juliClasspathDone

if not "%JSSE_OPTS%" == "" goto gotJsseOpts
set JSSE_OPTS="-Djdk.tls.ephemeralDHKeySize=2048"
:gotJsseOpts
set "JAVA_OPTS=%JAVA_OPTS% %JSSE_OPTS%"

rem Se registran manejadores de URL personalizados
rem Haga esto aqui de forma que los manejadores de URL personalizados (especificamente 'war:...') 
rem puedan ser utilizados en la politica de seguridad.
set "JAVA_OPTS=%JAVA_OPTS% -Djava.protocol.handler.pkgs=org.apache.catalina.webresources"

if not "%LOGGING_CONFIG%" == "" goto noJuliConfig
set LOGGING_CONFIG=-Dnop
if not exist "%CATALINA_BASE%\conf\logging.properties" goto noJuliConfig
set LOGGING_CONFIG=-Djava.util.logging.config.file="%CATALINA_BASE%\conf\logging.properties"
:noJuliConfig

if not "%LOGGING_MANAGER%" == "" goto noJuliManager
set LOGGING_MANAGER=-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
:noJuliManager

rem Configura parametros de arranque especificos de JAVA 9
set "JDK_JAVA_OPTIONS=%JDK_JAVA_OPTIONS% --add-opens=java.base/java.lang=ALL-UNNAMED"
set "JDK_JAVA_OPTIONS=%JDK_JAVA_OPTIONS% --add-opens=java.base/java.io=ALL-UNNAMED"
set "JDK_JAVA_OPTIONS=%JDK_JAVA_OPTIONS% --add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED"

rem Java 9 ya no soporta la propiedad del sistema 
rem java.endorsed.dirs. Intente usarla solo si 
rem JAVA_ENDORSED_DIRS fue establecida explicitamente
rem o exista CATALINA_HOME/endorsed.
set ENDORSED_PROP=ignore.endorsed.dirs
if "%JAVA_ENDORSED_DIRS%" == "" goto noEndorsedVar
set ENDORSED_PROP=java.endorsed.dirs
goto doneEndorsed
:noEndorsedVar
if not exist "%CATALINA_HOME%\endorsed" goto doneEndorsed
set ENDORSED_PROP=java.endorsed.dirs
:doneEndorsed

rem Agrega el agente java de OpenEJB
if not exist "%CATALINA_HOME%\lib\openejb-javaagent.jar" goto noOpenEJBJavaagent
set JAVA_OPTS="-javaagent:%CATALINA_HOME%\lib\openejb-javaagent.jar" %JAVA_OPTS%
:noOpenEJBJavaagent

rem ----- Ejecuta el Comando Requerido  ---------------------------------------

echo Usando CATALINA_BASE:   "%CATALINA_BASE%"
echo Usando CATALINA_HOME:   "%CATALINA_HOME%"
echo Usando CATALINA_TMPDIR: "%CATALINA_TMPDIR%"
if ""%1"" == ""debug"" goto use_jdk
echo Usando JRE_HOME:        "%JRE_HOME%"
goto java_dir_displayed
:use_jdk
echo Usando JAVA_HOME:       "%JAVA_HOME%"
:java_dir_displayed
echo Usando CLASSPATH:       "%CLASSPATH%"

set _EXECJAVA=%_RUNJAVA%
set MAINCLASS=org.apache.catalina.startup.Bootstrap
set ACTION=start
set SECURITY_POLICY_FILE=
set DEBUG_OPTS=
set JPDA=

if not ""%1"" == ""jpda"" goto noJpda
set JPDA=jpda
if not "%JPDA_TRANSPORT%" == "" goto gotJpdaTransport
set JPDA_TRANSPORT=dt_socket
:gotJpdaTransport
if not "%JPDA_ADDRESS%" == "" goto gotJpdaAddress
set JPDA_ADDRESS=localhost:8000
:gotJpdaAddress
if not "%JPDA_SUSPEND%" == "" goto gotJpdaSuspend
set JPDA_SUSPEND=n
:gotJpdaSuspend
if not "%JPDA_OPTS%" == "" goto gotJpdaOpts
set JPDA_OPTS=-agentlib:jdwp=transport=%JPDA_TRANSPORT%,address=%JPDA_ADDRESS%,server=y,suspend=%JPDA_SUSPEND%
:gotJpdaOpts
shift
:noJpda

if ""%1"" == ""debug"" goto doDebug
if ""%1"" == ""run"" goto doRun
if ""%1"" == ""start"" goto doStart
if ""%1"" == ""stop"" goto doStop
if ""%1"" == ""configtest"" goto doConfigTest
if ""%1"" == ""version"" goto doVersion

echo Uso:  catalina ( comandos ... )
echo comandos:
echo   debug             Inicia Catalina en un depurador
echo   debug -security   Depura Catalina con el gestor de seguridad
echo   jpda start        Inicia Catalina bajo el depurador JPDA
echo   run               Inicia Catalina en la ventana actual
echo   run -security     Inicia Catalina en la ventana actual con el gestor de seguridad
echo   start             Inicia Catalina en una ventana separada
echo   start -security   Inicia Catalina en una ventana separada con el gestor de seguridad
echo   stop              Para Catalina
echo   configtest        Ejecuta una comprobacion basica de sintaxis de server.xml
echo   version           Muestra la version de Tomcat instalada
goto end

:doDebug
shift
set _EXECJAVA=%_RUNJDB%
set DEBUG_OPTS=-sourcepath "%CATALINA_HOME%\..\..\java"
if not ""%1"" == ""-security"" goto execCmd
shift
echo Usando el Gestor de Seguridad
set "SECURITY_POLICY_FILE=%CATALINA_BASE%\conf\catalina.policy"
goto execCmd

:doRun
shift
if not ""%1"" == ""-security"" goto execCmd
shift
echo Usando el Gestor de Seguridad
set "SECURITY_POLICY_FILE=%CATALINA_BASE%\conf\catalina.policy"
goto execCmd

:doStart
shift
if "%TITLE%" == "" set TITLE=Tomcat
set _EXECJAVA=start "%TITLE%" %_RUNJAVA%
if not ""%1"" == ""-security"" goto execCmd
shift
echo Usando el Gestor de Seguridad
set "SECURITY_POLICY_FILE=%CATALINA_BASE%\conf\catalina.policy"
goto execCmd

:doStop
shift
set ACTION=stop
set CATALINA_OPTS=
goto execCmd

:doConfigTest
shift
set ACTION=configtest
set CATALINA_OPTS=
goto execCmd

:doVersion
%_EXECJAVA% -classpath "%CATALINA_HOME%\lib\catalina.jar" org.apache.catalina.util.ServerInfo
goto end


:execCmd
rem Se obtienen los argumentos restantes de la linea de comandos sin modificar y se guardan
set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

rem Ejecuta Java con las propiedades aplicables
if not "%JPDA%" == "" goto doJpda
if not "%SECURITY_POLICY_FILE%" == "" goto doSecurity
%_EXECJAVA% %LOGGING_CONFIG% %LOGGING_MANAGER% %JAVA_OPTS% %CATALINA_OPTS% %DEBUG_OPTS% -D%ENDORSED_PROP%="%JAVA_ENDORSED_DIRS%" -classpath "%CLASSPATH%" -Dcatalina.base="%CATALINA_BASE%" -Dcatalina.home="%CATALINA_HOME%" -Djava.io.tmpdir="%CATALINA_TMPDIR%" %MAINCLASS% %CMD_LINE_ARGS% %ACTION%
goto end
:doSecurity
%_EXECJAVA% %LOGGING_CONFIG% %LOGGING_MANAGER% %JAVA_OPTS% %CATALINA_OPTS% %DEBUG_OPTS% -D%ENDORSED_PROP%="%JAVA_ENDORSED_DIRS%" -classpath "%CLASSPATH%" -Djava.security.manager -Djava.security.policy=="%SECURITY_POLICY_FILE%" -Dcatalina.base="%CATALINA_BASE%" -Dcatalina.home="%CATALINA_HOME%" -Djava.io.tmpdir="%CATALINA_TMPDIR%" %MAINCLASS% %CMD_LINE_ARGS% %ACTION%
goto end
:doJpda
if not "%SECURITY_POLICY_FILE%" == "" goto doSecurityJpda
%_EXECJAVA% %LOGGING_CONFIG% %LOGGING_MANAGER% %JAVA_OPTS% %JPDA_OPTS% %CATALINA_OPTS% %DEBUG_OPTS% -D%ENDORSED_PROP%="%JAVA_ENDORSED_DIRS%" -classpath "%CLASSPATH%" -Dcatalina.base="%CATALINA_BASE%" -Dcatalina.home="%CATALINA_HOME%" -Djava.io.tmpdir="%CATALINA_TMPDIR%" %MAINCLASS% %CMD_LINE_ARGS% %ACTION%
goto end
:doSecurityJpda
%_EXECJAVA% %LOGGING_CONFIG% %LOGGING_MANAGER% %JAVA_OPTS% %JPDA_OPTS% %CATALINA_OPTS% %DEBUG_OPTS% -D%ENDORSED_PROP%="%JAVA_ENDORSED_DIRS%" -classpath "%CLASSPATH%" -Djava.security.manager -Djava.security.policy=="%SECURITY_POLICY_FILE%" -Dcatalina.base="%CATALINA_BASE%" -Dcatalina.home="%CATALINA_HOME%" -Djava.io.tmpdir="%CATALINA_TMPDIR%" %MAINCLASS% %CMD_LINE_ARGS% %ACTION%
goto end

:end
