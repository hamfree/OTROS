@rem Script para elegir version del JDK, personalizado para el pc ELSUPER
@rem jfruiz, 02/05/2002
@rem
@rem Configura el PATH del sistema colocando por orden de importancia las
@rem rutas a las aplicaciones del sistema operativo, agregando luego las
@rem rutas de las siguientes aplicaciones:
@rem - NIRSOFT (unidad D)
@rem - OpenSSL (unidad D)
@rem - FFmpeg  (unidad D)
@rem - Visual Studio Code (unidad D)
@rem - NMAP (unidad D)
@rem - Cliente SSH Bitvise (unidad D)
@rem
@rem Despues de agregar todas las rutas del sistema y aplicaciones antes
@rem indicadas, se construyen las rutas para las herramientas de desarrollo
@rem y servidores de aplicaciones, que se encuentran todos instalados bajo
@rem la ruta "E:\des\bin". Las aplicaciones cuyas rutas se agregan al path son:
@rem
@rem - JDK's (versiones 8, 11 y 17)
@rem - Maven
@rem - Git
@rem - GnuPG
@rem - Ant
@rem - Apache TomEE
@rem - Apache Derby
@rem - MySQL
@rem - Keystore Explorer
@rem - Scripts de consola y powershell (directorio 'scripts')
@rem - Otras utilidades (directorio 'utiles')
@rem - Node
@rem
@rem NOTA: El path original se encuentra en el archivo 'ELSUPER\path-original.txt'

@echo off
cls
chcp 1252

if not %COMPUTERNAME%==ELSUPER (
    echo En este equipo: '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)

@rem El disco de desarrollo en ELSUPER tiene la unidad E:
set DRIVE=E:

@rem En el path del sistema usamos PATHSYS para agregar las rutas mas 
@rem importantes primero
set PATHSYS=%SYSTEMROOT%\system32

@rem Variable PATHDES para componer las rutas de las herramientas desarrollo
set PATHDES=%DRIVE%\des\bin



@rem Variable necesaria para la ejecucion de Tomee
set CATALINA_HOME=%DRIVE%\des\bin\tomee

@rem Eliminamos el contenido actual de JAVA_HOME
set JAVA_HOME=

@rem iniciamos variable que guarda la seleccion de java del usuario
set jdkactivado=Ninguna

@rem Las apps de windows instaladas por el usuario y sus scripts de powershell
set WNDAPPS=%USERPROFILE%\AppData\Local\Microsoft\WindowsApps
set WPS=%USERPROFILE%\Documents\WindowsPowerShell\Scripts

@rem Las rutas estandar de Windows mas las aplicaciones universales del usuario 
@rem y su carpeta de scripts de PowerShell
set PATHSYS=%PATHSYS%;%SYSTEMROOT%
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\Wbem
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\WindowsPowerShell\v1.0
set PATHSYS=%PATHSYS%;%SYSTEMROOT%\System32\OpenSSH
set PATHSYS=%PATHSYS%;%WNDAPPS%
set PATHSYS=%PATHSYS%;%WPS%

@rem Las aplicaciones instaladas por mi en ELSUPER en la unidad C 
set PATHSYS=%PATHSYS%;%ProgramFiles%\dotnet\
set PATHSYS=%PATHSYS%;%ProgramFiles%\Microsoft SQL Server\130\Tools\Binn\
set PATHSYS=%PATHSYS%;%ProgramFiles%\NVIDIA Corporation\NVIDIA NvDLISR
set PATHSYS=%PATHSYS%;%ProgramFiles(x86)%\NVIDIA Corporation\PhysX\Common
set PATHSYS=%PATHSYS%;%ProgramFiles(x86)%\ZeroTier\One
set PATHSYS=%PATHSYS%;%ProgramFiles(x86)%\dotnet\

@rem Rutas para el perfil del usuario actual
set PATHSYS=%PATHSYS%;%USERPROFILE%\AppData\Local\Microsoft\WindowsApps
set PATHSYS=%PATHSYS%;%USERPROFILE%\.dotnet\tools

@rem Las aplicaciones instaladas por mi en ELSUPER en la unidad D
@rem Ruta de archivos de programa de 64 bit en la unidad D:
set PRF=D:\Program Files

@rem Ruta de archivos de programa de 32 bit en la unidad D:
set PRF86=D:\Program Files ^(x86^)
set PATHSYS=%PATHSYS%;%PRF%\Nirsoft
set PATHSYS=%PATHSYS%;%PRF%\OpenSSL\bin
set PATHSYS=%PATHSYS%;%PRF%\FFmpeg
set PATHSYS=%PATHSYS%;%PRF%\Microsoft VS Code\bin
set PATHSYS=%PATHSYS%;%PRF86%\Nmap 
set PATHSYS=%PATHSYS%;%PRF86%\Bitvise SSH Client

@rem Variables de entorno para cada una de las herramientas de desarrollo
set MVN=%PATHDES%\mvn\bin
set GIT=%PATHDES%\git\bin
set GITCMD=%PATHDES%\git\cmd
set GPG=%PATHDES%\GnuPG\bin
set ANT=%PATHDES%\ant\bin
set TOMEE=%PATHDES%\tomee\bin
set DERBY=%PATHDES%\derby\bin
set MYSQL=%PATHDES%\mysql\bin
set KSE=%PATHDES%\kse
set SCRIPTS=%PATHDES%\scripts
set UTILES=%PATHDES%\utiles
set NODE=%PATHDES%\node

@rem Componemos el path PATHDES de las herramientas de desarrollo.
set PATHDES=%PATHDES%;%JAVA_HOME%\bin
set PATHDES=%PATHDES%;%MVN%
set PATHDES=%PATHDES%;%GIT%
set PATHDES=%PATHDES%;%GITCMD%
set PATHDES=%PATHDES%;%GPG%
set PATHDES=%PATHDES%;%ANT%
set PATHDES=%PATHDES%;%TOMEE%
set PATHDES=%PATHDES%;%DERBY%
set PATHDES=%PATHDES%;%MYSQL%
set PATHDES=%PATHDES%;%KSE%
set PATHDES=%PATHDES%;%SCRIPTS%
set PATHDES=%PATHDES%;%UTILES%
set PATHDES=%PATHDES%;%NODE%

:Menu
cls
echo ***************************************************************************
echo *               MENU SELECCION VERSION DE ENTORNO DE JAVA                 *
echo ***************************************************************************
echo.
echo   Seleccione una version de Java indicando su numero en el menu
echo.
echo      1. JDK 8
echo      2. JDK 11
echo      3. JDK 17
echo      0. SALIR
echo.
echo      Equipo: %computername%
echo      Version seleccionada actual: %jdkactivado%
echo      JAVA_HOME: %java_home%
echo.
echo ***************************************************************************
echo.
@rem Recogida del valor del usuario y envio a las distintas opciones
set /p var=	
if %var%==1 goto :jdk8
if %var%==2 goto :jdk11
if %var%==3 goto :jdk17
if %var%==0 goto :salida
if %var% GTR 3 echo Error
goto :Menu

@rem Aqui estan las distintas opciones del menu
:jdk8
cls 
set jdkactivado=JDK 8
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

:jdk17
cls 
set jdkactivado=JDK 17
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%PATHDES%\jdk17
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
echo.
echo Pulse una tecla para componer el PATH del sistema...
pause>Nul

set PATH=
set PATH=%PATHSYS%;%PATHDES%

cls
echo Equipo: %computername%
echo Version de Java activada: %jdkactivado%
echo JAVA_HOME: %JAVA_HOME%
echo PATH actual: 
echo %PATH%

:EquipoNoValido
echo.
@rem Eliminamos variables de entorno usadas internamente en el script
set DRIVE=
set WNDAPPS=
set WPS=
set PATHSYS=
set PATHDES=
set PRF=
set PRF86=
set ANT=
set CODE=
set GIT=
set GITCMD=
set GPG=
set KSE=
set MVN=
set MYSQL=
set OPENSSL=
set SCRIPTS=
set TOMEE=
set UTILES=
set NODE=
set jdkactivado=
set var=
echo.