@rem ************************************************************************************
@rem *    Script para elegir version del JDK, personalizado para el portatil FILEMON    *
@rem *    Configura el PATH del sistema colocando por orden de importancia las rutas    *
@rem *    a las aplicaciones del sistema operativo, agregando luego las rutas de las    *
@rem *    siguientes aplicaciones:                                                      *
@rem *    - Putty                                                                       *
@rem *    - Calibre2                                                                    *
@rem *    - GnuPG                                                                       *
@rem *    - ZeroTier                                                                    *
@rem *    - MySQL                                                                       *
@rem *    Despues de agregar todas las rutas del sistema y aplicaciones antes           *
@rem *    indicadas, se construyen las rutas para las herramientas de desarrollo        *
@rem *    y servidores de aplicaciones, que se encuentran todos instalados bajo         *
@rem *    la ruta "C:\des\bin". Las aplicaciones cuyas rutas se agregan al path         *
@rem *    son:                                                                          *
@rem *    - OpenSSL                                                                     *
@rem *    - Git                                                                         *
@rem *    - Lenguaje de programacion 'Python'                                           *
@rem *    - Servidor J2EE Tomee                                                         *
@rem *    - Contenedor de servlets/JSP Tomcat                                           *
@rem *    - Lenguaje de programacion Java (versiones 1.8, 11)                           *
@rem *    - Directorio con scripts                                                      *
@rem *    - Directorio con utilidades                                                   *
@rem *                                                                                  *
@rem *    Historia:                                                                     *
@rem *    - 2020/04/02 - Se agregan las rutas del software de Intel y Nvidia.           *
@rem *      Se cambian las rutas de desarrollo.                                         *
@rem *                                                                                  *
@rem ************************************************************************************

@echo off
@chcp 1252 > nul

if not %COMPUTERNAME%==FILEMON (
    echo En este equipo de nombre '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)

@rem Las apps de windows instaladas por el usuario y sus scripts de powershell
set WINDOWSAPPS=%USERPROFILE%\AppData\Local\Microsoft\WindowsApps
set POWERSHELL=%USERPROFILE%\Documents\WindowsPowerShell\Scripts

@rem En el path del sistema las rutas mas importantes primero
set PATHSYS=%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\system32\Wbem
set PATHSYS=%PATHSYS%;%SystemRoot%\system32\WindowsPowerShell\v1.0
set PATHSYS=%PATHSYS%;%SystemRoot%\system32\OpenSSH
set PATHSYS=%PATHSYS%;%WINDOWSAPPS%
set PATHSYS=%PATHSYS%;%POWERSHELL%

@rem El resto de rutas de otras aplicaciones incluidas en el path del sistema
set PATHSYS=%PATHSYS%;C:\Program Files\PuTTY
set PATHSYS=%PATHSYS%;C:\Program Files\Calibre2
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\gnupg\bin
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\ZeroTier\One
set PATHSYS=%PATHSYS%;C:\Program Files\MySQL\MySQL Server 8.0\bin
set PATHSYS=%PATHSYS%;C:\Program Files\Intel\Intel^(R^) Management Engine Components\DAL
set PATHSYS=%PATHSYS%;C:\Program Files\Intel\Intel^(R^) Management Engine Components\IPT
set PATHSYS=%PATHSYS%;C:\Program Files\Intel\iCLS Client\
set PATHSYS=%PATHSYS%;C:\Program Files\Intel\WiFi\bin\
set PATHSYS=%PATHSYS%;C:\Program Files\NVIDIA Corporation\NVIDIA NvDLISR
set PATHSYS=%PATHSYS%;C:\Program Files\Common Files\Intel\WirelessCommon\
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\Intel\Intel^(R^) Management Engine Components\IPT
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\Intel\Intel^(R^) Management Engine Components\DAL
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\Intel\iCLS Client\
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\NVIDIA Corporation\PhysX\Common


@rem Variables para herramientas del entorno de desarrollo
set PATHDES=C:\des\bin

@rem Variable necesaria para la ejecución de Tomcat
set CATALINA_HOME=C:\des\bin\tomcat

@rem Aqui van las rutas a los binarios de las herramientas de desarrollo.
set OPENSSL=%PATHDES%\openssl\bin
set GIT=%PATHDES%\git\bin
set PYTHON=%PATHDES%\python;%PATHDES%\python\Scripts
set TOMCAT=%PATHDES%\tomcat\bin
set TOMEE=%PATHDES%\tomee\bin
set SCRIPT=%PATHDES%\scripts
set UTIL=%PATHDES%\utiles

@rem Componemos el path de las herramientas de desarrollo.
set PATHDES=%PATHDES%;%TOMCAT%;%TOMEE%;%PYTHON%;%GIT%;%SCRIPT%;%UTIL%

@rem Eliminamos el contenido actual de JAVA_HOME
set JAVA_HOME=

:Menu
cls
echo.
echo SETJDK.CMD - Permite al usuario seleccionar un JDK para usar de los instalados en el sistema
echo.
echo   Seleccione una version de Java indicando su numero en el menu
echo.
echo.
echo      1. JDK 1.8
echo      2. JDK 11
echo      0. SALIR
echo.
echo.
@rem Recogida del valor del usuario y envio a las distintas opciones
set /p var=
if %var%==1 goto :jdk8
if %var%==2 goto :jdk11
if %var%==0 goto :salida
if %var% GTR 2 goto :Error
goto :Menu

:Error
cls 
echo ERROR: La opcion elegida no es valida. Debe indicar un valor entre 0 y 2
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem Entorno de desarrollo JDK 8
:jdk8
cls 
set jdkactivado=JDK 1.8
echo Estableciendo JAVA_HOME 
set JAVA_HOME=C:\des\bin\jdk8
echo Estableciendo PATH 
set PATHDES=%JAVA_HOME%\bin;%PATHDES%
echo Version de java que se activara: %jdkactivado%
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem Entorno de desarrollo JDK 11
:jdk11
cls 
set jdkactivado=JDK 11
echo Estableciendo JAVA_HOME 
set JAVA_HOME=C:\des\bin\jdk11
echo Estableciendo PATH 
set PATHDES=%JAVA_HOME%\bin;%PATHDES%
echo Version de java que se activara: %jdkactivado%
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem A la salida reiniciamos el PATH del sistema....
:salida

set PATH=
set PATH=%PATHSYS%;%PATHDES%

cls
echo Version de Java activada: %jdkactivado%
echo PATH actual: 
echo %PATH%
echo.

:EquipoNoValido
@rem Eliminamos variables de entorno usadas internamente en el script
set WINDOWSAPPS=
set POWERSHELL=
set PATHSYS=
set PATHDES=
set OPENSSL=
set GIT=
set PYTHON=
set TOMCAT=
set TOMEE=
set jdkactivado=
set var=
echo.