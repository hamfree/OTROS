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
@rem *    - Servidor NodeJS                                                             *
@rem *    - Git                                                                         *
@rem *    - npm (gestor de paquetes de NodeJs)                                          *
@rem *    - Lenguaje de programaci�n 'Python'                                           *
@rem *    - Lenguaje de programaci�n 'Perl'                                             *
@rem *    - Maven                                                                       *
@rem *    - Gradle                                                                      *
@rem *    - Servidor J2EE Glassfish                                                     *
@rem *    - Servidor J2EE Payara                                                        *
@rem *    - Contenedor de servlets/JSP Tomcat                                           *
@rem *    - Lenguaje de programaci�n Java (versiones 1.8, 11 y 13)                      *
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
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\GnuPG\bin
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\ZeroTier\One
set PATHSYS=%PATHSYS%;C:\Program Files\MySQL\MySQL Server 8.0\bin

@rem Variables para herramientas del entorno de desarrollo
set PATHDES=C:\des\bin

@rem Aqui van las rutas a los binarios de las herramientas de desarrollo.
set OPENSSL=%PATHDES%\openssl\bin
set NODE=%PATHDES%\nodejs
set GIT=%PATHDES%\git\bin
set NPM=%USERPROFILE%\AppData\Roaming\npm
set PYTHON=%PATHDES%\python;%PATHDES%\python\Scripts;C:\Users\hamfree\AppData\Roaming\Python\Python38\Scripts
set PERL=%PATHDES%\perl\site\bin;%PATHDES%\perl\bin
set MVN_HOME=%PATHDES%\maven
set GRADLE_HOME=%PATHDES%\gradle
set GLASSFISH=%PATHDES%\glassfish\bin;%PATHDES%\glassfish\glassfish\bin
set TOMCAT=%PATHDES%\tomcat\bin
set PAYARA=%PATHDES%\payara\bin;%PATHDES%\payara\glassfish\bin

@rem Componemos el path de las herramientas de desarrollo.
set PATHDES=%PATHDES%;%MVN_HOME%\bin;%GRADLE_HOME%\bin;%GLASSFISH%;%TOMCAT%;%PAYARA%;%PYTHON%;%PERL%;%NODE%;%NPM%;%GIT%

@rem Eliminamos el contenido actual de JAVA_HOME
set JAVA_HOME=

:Menu
cls
echo.
echo SETJDK.CMD - Permite al usuario seleccionar un JDK para usar de los instalados en el sistema
echo.
echo   Seleccione una version de Java indicando su n�mero en el men�
echo.
echo.
echo      1. JDK 1.8
echo      2. JDK 11
echo      3. JDK 13
echo      0. SALIR
echo.
echo.
@rem Recogida del valor del usuario y env�o a las distintas opciones
set /p var=
if %var%==1 goto :jdk8
if %var%==2 goto :jdk11
if %var%==3 goto :jdk13
if %var%==0 goto :salida
if %var% GTR 3 goto :Error
goto :Menu

:Error
cls 
echo ERROR: La opcion elegida no es valida. Debe indicar un valor entre 0 y 3
echo Presiona una tecla para volver al men�
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
echo Versi�n de java que se activar�: %jdkactivado%
echo Presiona una tecla para volver al men�
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
echo Versi�n de java que se activar�: %jdkactivado%
echo Presiona una tecla para volver al men�
pause > Nul
goto :Menu

@rem Entorno de desarrollo JDK 13
:jdk13
cls 
set jdkactivado=JDK 13
echo Estableciendo JAVA_HOME 
set JAVA_HOME=C:\des\bin\jdk13
echo Estableciendo PATH 
set PATHDES=%JAVA_HOME%\bin;%PATHDES%
echo Versi�n de java que se activar�: %jdkactivado%
echo Presiona una tecla para volver al men�
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
echo.