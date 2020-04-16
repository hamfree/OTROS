@rem ************************************************************************************
@rem *                                                                                  *
@rem *    Script para elegir version del JDK, personalizado para el portatil ELSUPER    *
@rem *    Configura el PATH del sistema colocando por orden de importancia las rutas    *
@rem *    a las aplicaciones del sistema operativo, agregando luego las rutas de las    *
@rem *    siguientes aplicaciones:                                                      *
@rem *    - Calibre2                                                                    *
@rem *    - GnuPG                                                                       *
@rem *    - Putty                                                                       *
@rem *    - ZeroTier                                                                    *
@rem *                                                                                  *
@rem *    Despues de agregar todas las rutas del sistema y aplicaciones antes           *
@rem *    indicadas, se construyen las rutas para las herramientas de desarrollo        *
@rem *    y servidores de aplicaciones, que se encuentran todos instalados bajo         *
@rem *    la ruta "C:\des\bin". Las aplicaciones cuyas rutas se agregan al path         *
@rem *    son:                                                                          *
@rem *    - Ant                                                                         *
@rem *    - Code                                                                        *
@rem *    - Git                                                                         *
@rem *    - JDK's (versiones 1.8, 11 y 14)                                              *
@rem *    - Maven                                                                       *
@rem *    - OpenSSL                                                                     *
@rem *    - Python                                                                      *
@rem *    - Scripts de consola y powershell                                             *
@rem *    - Utiles  de SysInternals y otras                                             *
@rem *                                                                                  *
@rem ************************************************************************************

@echo off
cls
chcp 1252

if not %COMPUTERNAME%==ELSUPER (
    echo En este equipo de nombre '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)

@rem En el path del sistema usamos PATHSYS para agregar las rutas mas importantes primero
set PATHSYS=%SYSTEMROOT%\system32

@rem Variable PATHDES para componer las rutas de las herramientas desarrollo
set PATHDES=D:\des\bin

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
echo      3. JDK 14
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
if %var%==3 goto :jdk14
if %var%==0 goto :salida
if %var% GTR 3 echo Error
goto :Menu

@rem Aqui estan las distintas opciones del menu
:jdk8
cls 
set jdkactivado=JDK 1.8
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%PATHDES%\jdk8
echo Version de java que se activara
%JAVA_HOME%\bin\java -version
echo Presione una tecla para volver al menu
pause>Nul
goto :Menu

:jdk11
cls 
set jdkactivado=JDK 11
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%PATHDES%\jdk11
echo Version de java que se activara
%JAVA_HOME%\bin\java -version
echo Presione una tecla para volver al menu
pause>Nul
goto :Menu

:jdk14
cls 
set jdkactivado=JDK 14
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%PATHDES%\jdk14
echo Version de java que se activara
%JAVA_HOME%\bin\java -version
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

cls
@rem Las apps de windows instaladas por el usuario y sus scripts de powershell
set WNDAPPS=%USERPROFILE%\AppData\Local\Microsoft\WindowsApps
set WPS=%USERPROFILE%\Documents\WindowsPowerShell\Scripts

@rem Las rutas estandar de Windows mas las aplicaciones universales del usuario y su carpeta de scripts de PowerShell
echo Agregando rutas estandar de Windows...
set PATHSYS=%PATHSYS%;%SYSTEMROOT%
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\Wbem
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\WindowsPowerShell\v1.0
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\OpenSSH
set PATHSYS=%PATHSYS%;%WNDAPPS%
set PATHSYS=%PATHSYS%;%WPS%

@rem Las aplicaciones instaladas por mi en ELSUPER (los parentesis se escapn con '^')
echo Agregando Calibre...
set PATHSYS=%PATHSYS%;C:\Program Files\Calibre2
echo Agregando GnuPG...
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\GnuPG\bin
echo Agregando Putty y herramientas asociadas...
set PATHSYS=%PATHSYS%;C:\Program Files\PuTTY
echo Agregando ZeroTier...
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\ZeroTier\One

@rem Configurando variables de entorno para cada una de las herramientas de desarrollo
set ANT_HOME=%PATHDES%\ant
set CODE_HOME=%PATHDES%\code
set GIT_HOME=%PATHDES%\Git
set GIT_SSH=C:\Program Files\PuTTY\plink.exe
set MVN_HOME=%PATHDES%\mvn
set MYSQL_HOME=%PATHDES%\mysql
set OPENSSL_HOME=%PATHDES%\openssl
set PYTHON_HOME=%PATHDES%\python
set SCRIPTS_HOME=%PATHDES%\scripts
set UTILES_HOME=%PATHDES%\utiles
set SVN_SSH=C:\Program Files\PuTTY\plink.exe

@rem Componemos el path PATHDES de las herramientas de desarrollo.
echo Agregando ruta de Ant al PATH...
set PATHDES=%PATHDES%;%ANT_HOME%\bin
echo Agregando ruta de Microsoft Code al PATH...
set PATHDES=%PATHDES%;%CODE_HOME%
echo Agregando ruta de Git al PATH...
set PATHDES=%PATHDES%;%GIT_HOME%\bin
echo Agregando ruta del JDK al PATH...
set PATHDES=%PATHDES%;%JAVA_HOME%\bin
echo Agregando ruta de Maven al PATH...
set PATHDES=%PATHDES%;%MVN_HOME%\bin
echo Agregando ruta de MySQL al PATH...
set PATHDES=%PATHDES%;%MYSQL_HOME%\bin
echo Agregando ruta de OpenSSL al PATH...
set PATHDES=%PATHDES%;%OPENSSL_HOME%\bin
echo Agregando ruta de Python al PATH...
set PATHDES=%PATHDES%;%PYTHON_HOME%
echo Agregando rutas de scripts y utilidades al PATH...
set PATHDES=%PATHDES%;%SCRIPTS_HOME%
set PATHDES=%PATHDES%;%UTILES_HOME%

echo.
echo Pulse una tecla para componer el PATH del sistema...
pause>Nul

set PATH=
set PATH=%PATHSYS%;%PATHDES%

cls
echo Version de Java activada: %jdkactivado%
echo JAVA_HOME: %JAVA_HOME%
echo PATH actual: 
echo %SYSTEMROOT%
echo %SYSTEMROOT%\System32\Wbem
echo %SYSTEMROOT%\System32\WindowsPowerShell\v1.0
echo %SYSTEMROOT%\System32\OpenSSH
echo %WNDAPPS%
echo %WPS%
echo C:\Program Files\Calibre2
echo C:\Program Files ^(x86^)\GnuPG\bin
echo C:\Program Files\PuTTY
echo C:\Program Files ^(x86^)\ZeroTier\One
echo D:\des\bin
echo %ANT_HOME%\bin
echo %CODE_HOME%
echo %GIT_HOME%\bin
echo %JAVA_HOME%\bin
echo %MVN_HOME%\bin
echo %MYSQL_HOME%\bin
echo %OPENSSL_HOME%\bin
echo %PYTHON_HOME%
echo %SCRIPTS_HOME%
echo %UTILES_HOME%

:EquipoNoValido
echo.
