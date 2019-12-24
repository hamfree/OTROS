@rem ************************************************************************************
@rem *    Script para elegir version del JDK, personalizado para el portatil ELSUPER    *
@rem *    Configura el PATH del sistema colocando por orden de importancia las rutas    *
@rem *    a las aplicaciones del sistema operativo, agregando luego las rutas de las    *
@rem *    siguientes aplicaciones:                                                      *
@rem *    - Putty                                                                       *
@rem *    - FFmpeg2                                                                     *
@rem *    - Calibre2                                                                    *
@rem *    - GnuPG                                                                       *
@rem *    - ZeroTier                                                                    *
@rem *    - MySQL                                                                       *
@rem *    - Nmap                                                                        *
@rem *    Despues de agregar todas las rutas del sistema y aplicaciones antes           *
@rem *    indicadas, se construyen las rutas para las herramientas de desarrollo        *
@rem *    y servidores de aplicaciones, que se encuentran todos instalados bajo         *
@rem *    la ruta "C:\des\bin". Las aplicaciones cuyas rutas se agregan al path         *
@rem *    son:                                                                          *
@rem *    - Ant                                                                         *
@rem *    - Maven                                                                       *
@rem *    - Gradle                                                                      *
@rem *    - Grails                                                                      *
@rem *    - Groovy                                                                      *
@rem *    - Git                                                                         *
@rem *    - Servidor NodeJS                                                             *
@rem *    - npm (gestor de paquetes de NodeJs)                                          *
@rem *    - Servidor Oracle                                                             *
@rem *    - Lenguaje de programación Java (versiones 1.8, 11 y 13)                      *
@rem ************************************************************************************

@echo off
cls
chcp 1252

if not %COMPUTERNAME%==ELSUPER (
    echo En este equipo de nombre '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)

@rem Las apps de windows instaladas por el usuario y sus scripts de powershell
set WNDAPPS=%USERPROFILE%\AppData\Local\Microsoft\WindowsApps
set WPS=%USERPROFILE%\Documents\WindowsPowerShell\Scripts

@rem En el path del sistema las rutas mas importantes primero
set PATHSYS=%SYSTEMROOT%\system32
set PATHSYS=%PATHSYS%;%SYSTEMROOT%
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\Wbem
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\WindowsPowerShell\v1.0
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\OpenSSH
set PATHSYS=%PATHSYS%;%WNDAPPS%
set PATHSYS=%PATHSYS%;%WPS%
set PATHSYS=%PATHSYS%;%ORACLE_HOME%\bin
set PATHSYS=%PATHSYS%;F:\des\bin\mysql\bin
set PATHSYS=%PATHSYS%;C:\Program Files\Ffmpeg
set PATHSYS=%PATHSYS%;C:\Program Files\Calibre2
set PATHSYS=%PATHSYS%;C:\Program Files\PuTTY
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\Nmap
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\GnuPG\bin
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\ZeroTier\One


@rem Variables para herramientas del entorno de desarrollo
set PATHDES=C:\des\bin


@rem Configurando variables de entorno para las herramientas de desarrollo
set JAVA_HOME=%PATHDES%\jdk8
set ANT_HOME=%PATHDES%\ant
set MVN_HOME=%PATHDES%\mvn
set GRADLE_HOME=%PATHDES%\gradle
set GRAILS_HOME=%PATHDES%\grails
set GROOVY_HOME=%PATHDES%\groovy
set GIT_HOME=C:\Program Files\Git
set GIT_SSH=C:\Program Files\PuTTY\plink.exe
set NODE_HOME=%PATHDES%\nodejs
set NPM=%USERPROFILE%\AppData\Roaming\npm
set SVN_SSH=C:\Program Files\PuTTY\plink.exe
set ORACLE_HOME=D:\Oracle\Soft
set OPENSSL_HOME=%PATHDES%\openssl


@rem Componemos el path de las herramientas de desarrollo.
set PATHDES=%PATHDES%;%ANT_HOME%\bin
set PATHDES=%PATHDES%;%MVN_HOME%\bin
set PATHDES=%PATHDES%;%GRADLE_HOME%\bin
set PATHDES=%PATHDES%;%GRAILS_HOME%\bin
set PATHDES=%PATHDES%;%GIT_HOME%\bin
set PATHDES=%PATHDES%;%NODE_HOME%\bin
set PATHDES=%PATHDES%;%NPM%
set PATHDES=%PATHDES%;%ORACLE_HOME%\bin
set PATHDES=%PATHDES%;%OPENSSL_HOME%\bin


@rem Eliminamos el contenido actual de JAVA_HOME
set JAVA_HOME=

@rem iniciamos variable que guarda la seleccion de java del usuario
set jdkactivado=Ninguna

:Menu
cls
echo ********************************************************************
echo *            MENU SELECCION VERSION DE ENTORNO DE JAVA             *
echo ********************************************************************
echo.
echo   Seleccione una version de Java indicando su numero en el menu
echo.
echo      1. JDK 1.8
echo      2. JDK 11
echo      3. JDK 13
echo      0. SALIR
echo.
echo      Version seleccionada actual: %jdkactivado%
echo      JAVA_HOME: %java_home%
echo.
echo ********************************************************************
echo.
@rem Recogida del valor del usuario y envio a las distintas opciones
set /p var=	
if %var%==1 goto :jdk8
if %var%==2 goto :jdk11
if %var%==3 goto :jdk13
if %var%==0 goto :salida
if %var% GTR 3 echo Error
goto :Menu

@rem Aqui estan las distintas opciones del menu
:jdk8
cls 
set jdkactivado=JDK 1.8
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%PATHDES%\jdk8
echo Estableciendo PATH 
set PATHDES=%PATHDES%;%JAVA_HOME%\bin;
echo Version de java que se activara
java -version
echo Presione una tecla para volver al menu
pause>Nul
goto :Menu

:jdk11
cls 
set jdkactivado=JDK 11
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%PATHDES%\jdk11
echo Estableciendo PATH 
set PATHDES=%PATHDES%;%JAVA_HOME%\bin;
echo Version de java que se activara
java -version
echo Presione una tecla para volver al menu
pause>Nul
goto :Menu

:jdk13
cls 
set jdkactivado=JDK 13
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%PATHDES%\jdk13
echo Estableciendo PATH 
set PATHDES=%PATHDES%;%JAVA_HOME%\bin;
echo Version de java que se activara
java -version
echo Presione una tecla para volver al menu
pause>Nul
goto :Menu
:Error
echo * ERROR - Ha elegido una opcion incorrecta *
echo Presione una tecla para volver al menu
pause>Nul
goto :Menu

@rem A la salida reiniciamos el PATH del sistema....
:salida

set PATH=
set PATH=%PATHSYS%;%PATHDES%

cls
echo Version de Java activada: %jdkactivado%
echo JAVA_HOME: %JAVA_HOME%
echo PATH actual: 
echo %PATH%
echo.

:EquipoNoValido
echo.