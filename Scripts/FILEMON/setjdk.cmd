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
@rem *    - 2020/06/01 - Se agrega la ruta de la utilidad Maven.                        *
@rem *      Se eliminan algunas rutas de aplicaciones no instaladas.                    *
@rem *    - 2020/06/11 - Se agrega la opcion de activar la version 14 del JDK.          *
@rem *    - 2020/10/28 - Se elimina la opcion del JDK 8 y se cambia JDK14 por JDK15     *
@rem *    - 2021/01/23 - Se agrega de nuevo la opcion del JDK 8.                        *
@rem *    - 2021/05/05 - Se cambia la version 15 de JDK por la 16.                      *
@rem *    - 2021/05/26 - Se genera una nueva variable para la unidad donde se           *
@rem *                   encuentra la instalación de desarrollo, DRIVE.                 * 
@rem *                                                                                  *
@rem ************************************************************************************

@echo off
@chcp 1252 > nul

if not %COMPUTERNAME%==FILEMON (
    echo En este equipo de nombre '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)

@rem El disco de desarrollo en FILEMON es la unidad C:
set DRIVE=C:

:Menu
cls
echo.
echo SETJDK.CMD - Permite al usuario seleccionar un JDK para usar de los instalados en el sistema
echo.
echo   Seleccione una version de Java indicando su numero en el menu
echo.
echo.
echo      1. JDK 8
echo      2. JDK 11
echo      3. JDK 16
echo      0. SALIR
echo.
echo.
echo      Equipo: %COMPUTERNAME%
echo      Version seleccionada actual: %jdkactivado%
echo      JAVA_HOME: %JAVA_HOME%
echo.
@rem Recogida del valor del usuario y envio a las distintas opciones
set /p var=
if %var%==1 goto :jdk8
if %var%==2 goto :jdk11
if %var%==3 goto :jdk16
if %var%==0 goto :salida
if %var% GTR 3 goto :Error
goto :Menu

:Error
cls 
echo ERROR: La opcion elegida no es valida. Debe indicar un valor entre 0 y 3
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem Entorno de desarrollo JDK 8
:jdk8
cls 
set jdkactivado=JDK 8
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%DRIVE%\des\bin\jdk8
echo Estableciendo PATH 
echo Version de java que se activara:
%JAVA_HOME%\bin\java -XshowSettings:vm -version
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem Entorno de desarrollo JDK 11
:jdk11
cls 
set jdkactivado=JDK 11
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%DRIVE%\des\bin\jdk11
echo Estableciendo PATH 
echo Version de java que se activara:
%JAVA_HOME%\bin\java -XshowSettings:vm -version
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem Entorno de desarrollo JDK 16
:jdk16
cls 
set jdkactivado=JDK 16
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%DRIVE%\des\bin\jdk16
echo Estableciendo PATH 
echo Version de java que se activara: 
%JAVA_HOME%\bin\java -XshowSettings:vm -version
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem A la salida reiniciamos el PATH del sistema....
:salida

cls
@rem Las apps de windows instaladas por el usuario y sus scripts de powershell
set WINDOWSAPPS=%USERPROFILE%\AppData\Local\Microsoft\WindowsApps
set POWERSHELL=%USERPROFILE%\Documents\WindowsPowerShell\Scripts

@rem En el path del sistema las rutas mas importantes primero
echo Agregando rutas estandar de Windows...
set PATHSYS=%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\system32\Wbem
set PATHSYS=%PATHSYS%;%SystemRoot%\system32\WindowsPowerShell\v1.0
set PATHSYS=%PATHSYS%;%SystemRoot%\system32\OpenSSH
set PATHSYS=%PATHSYS%;%WINDOWSAPPS%
set PATHSYS=%PATHSYS%;%POWERSHELL%

@rem El resto de rutas de otras aplicaciones incluidas en el path del sistema
echo Agregando Calibre...
set PATHSYS=%PATHSYS%;C:\Program Files\Calibre2
echo Agregando GnuPG...
set PATHSYS=%PATHSYS%;C:\Program Files ^(x86^)\gnupg\bin


@rem Variables para herramientas del entorno de desarrollo
set PATHDES=%DRIVE%\des\bin

@rem Aqui van las rutas a los binarios de las herramientas de desarrollo.
set ANT=%PATHDES%\ant\bin
set GIT=%PATHDES%\git\bin
set MVN=%PATHDES%\mvn\bin
set MYSQL=%PATHDES%\mysql\bin
set OPENSSL=%PATHDES%\openssl\bin
set TOMEE=%PATHDES%\tomee\bin
set SCRIPT=%PATHDES%\scripts
set UTIL=%PATHDES%\utiles


@rem Variable necesaria para la ejecucion de Tomcat
set CATALINA_HOME=%DRIVE%\des\bin\tomcat

@rem Agregamos la ruta a los ejecutables del entorno de Java elegido
set PATHDES=%PATHDES%;%JAVA_HOME%\bin


@rem Componemos el path de las herramientas de desarrollo.
set PATHDES=%PATHDES%;%GIT%;%MVN%;%OPENSSL%;%PYTHON%;%TOMCAT%;%TOMEE%;%SCRIPT%;%UTIL%

@rem Componemos el path PATHDES de las herramientas de desarrollo.
echo Agregando %ANT% al PATH de desarrollo...
set PATHDES=%PATHDES%;%ANT%
echo Agregando %GIT% al PATH de desarrollo...
set PATHDES=%PATHDES%;%GIT%
echo Agregando %MVN% al PATH de desarrollo...
set PATHDES=%PATHDES%;%MVN%
echo Agregando %MYSQL% al PATH de desarrollo...
set PATHDES=%PATHDES%;%MYSQL%
echo Agregando %OPENSSL% al PATH de desarrollo...
set PATHDES=%PATHDES%;%OPENSSL%
echo Agregando %TOMEE% al PATH de desarrollo...
set PATHDES=%PATHDES%;%TOMEE%
echo Agregando %SCRIPT% al PATH de desarrollo...
set PATHDES=%PATHDES%;%SCRIPT%
echo Agregando %UTIL% al PATH de desarrollo...
set PATHDES=%PATHDES%;%UTIL%

echo.
echo Pulse una tecla para componer el PATH del sistema...
pause>Nul

@rem Componemos la variable del sistema PATH
set PATH=
set PATH=%PATHSYS%;%PATHDES%

cls
echo Equipo: %computername%
echo Version de Java activada:
java -XshowSettings:vm -version
echo PATH actual: 
echo %PATH%
echo.

:EquipoNoValido
@rem Eliminamos variables de entorno usadas internamente en el script
set ANT=
set DRIVE=
set WINDOWSAPPS=
set POWERSHELL=
set PATHSYS=
set PATHDES=
set OPENSSL=
set GIT=
set MVN=
set TOMEE=
set jdkactivado=
set var=
echo.