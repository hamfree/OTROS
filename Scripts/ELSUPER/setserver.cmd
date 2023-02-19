@rem ************************************************************************************
@rem *                                                                                  *
@rem *    Script para elegir la version del servidor aplicaciones a ejecutar,           *
@rem *    personalizado para el pc ELSUPER:                                             *
@rem *    - Contenedor de servlets/JSP TOMCAT                                           *
@rem *    - Servidor J2EE TOMEE                                                         *
@rem *                                                                                  *
@rem ************************************************************************************

@echo off
@chcp 1252 > nul

if not %COMPUTERNAME%==ELSUPER (
    echo En este equipo de nombre '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)


@rem El disco de desarrollo en ELSUPER tiene la unidad E:
set DRIVE=E:


@rem En el path del sistema usamos PATHSYS para agregar las rutas mas 
@rem importantes primero
set PATHSYS=%SYSTEMROOT%\system32

@rem Variables para herramientas del entorno de desarrollo
set PATHDES=%DRIVE%\des\bin

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
set PATHSYS=%PATHSYS%;%PRF%\FFmpeg
set PATHSYS=%PATHSYS%;%PRF%\Microsoft VS Code\bin
set PATHSYS=%PATHSYS%;%PRF86%\Bitvise SSH Client

@rem Variables de entorno para cada una de las herramientas de desarrollo
set ANT=%PATHDES%\ant\bin
set DERBY=%PATHDES%\derby\bin
set GIT=%PATHDES%\git\bin
set GITCMD=%PATHDES%\git\cmd
set GPG=%PATHDES%\GnuPG\bin
set KSE=%PATHDES%\kse
set MVN=%PATHDES%\mvn\bin
set MYSQL=%PATHDES%\mysql\bin
set NODE=%PATHDES%\node
set OPENSSL=%PATHDES%\openssl\bin
set SCRIPTS=%PATHDES%\scripts
set UTILES=%PATHDES%\utiles

@rem Aquí fijamos como JDK la versión 17.
set JAVA_HOME=%PATHDES%\jdk17


@rem Menú para seleccionar el servidor de aplicaciones por defecto
:Menu
cls
echo.
echo SETSERVER.CMD - Permite al usuario seleccionar un servidor de aplicaciones para usar 
echo de los instalados en el sistema
echo.
echo   Seleccione un servidor de aplicaciones indicando su numero en el menu
echo.
echo.
echo      1. Contenedor de Servlets/JSP TOMCAT
echo      2. Servidor J2EE TOMEE
echo      0. SALIR
echo.
echo.
@rem Recogida del valor del usuario y envio a las distintas opciones
set /p var=
if %var%==1 goto :tomcat
if %var%==2 goto :tomee
if %var%==0 goto :salida
if %var% GTR 2 goto :Error
goto :Menu

:Error
cls 
echo ERROR: La opcion elegida no es valida. Debe indicar un valor entre 0 y 2
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem Contenedor de Servlets/JSP TOMCAT
:tomcat
cls 
set CATALINA_HOME=%PATHDES%\tomcat
echo Estableciendo TOMCAT en la ruta %CATALINA_HOME% como servidor
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem Servidor J2EE TOMEE
:tomee
cls 
set CATALINA_HOME=%PATHDES%\tomee
echo Estableciendo TOMEE en la ruta %CATALINA_HOME% como servidor
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem A la salida reiniciamos el PATH del sistema....
:salida

cls
@rem Componemos el path PATHDES de las herramientas de desarrollo.
set PATHDES=%PATHDES%;%ANT%
set PATHDES=%PATHDES%;%DERBY%
set PATHDES=%PATHDES%;%GIT%
set PATHDES=%PATHDES%;%GITCMD%
set PATHDES=%PATHDES%;%GPG%
set PATHDES=%PATHDES%;%JAVA_HOME%\bin
set PATHDES=%PATHDES%;%KSE%
set PATHDES=%PATHDES%;%MVN%
set PATHDES=%PATHDES%;%MYSQL%
set PATHDES=%PATHDES%;%NODE%
set PATHDES=%PATHDES%;%OPENSSL%
set PATHDES=%PATHDES%;%SCRIPTS%

@rem La ruta de los binarios de tomcat/tomee dependerá del CATALINA_HOME elegido
set PATHDES=%PATHDES%;%CATALINA_HOME%\bin

set PATHDES=%PATHDES%;%UTILES%


set PATH=
set PATH=%PATHSYS%;%PATHDES%
echo PATH actual:
echo %PATH%
echo JAVA_HOME actual:
echo %JAVA_HOME%
echo CATALINA_HOME actual: 
echo %CATALINA_HOME%
echo.

:EquipoNoValido
@rem Eliminamos variables de entorno usadas internamente en el script
set var=
set PATHDES=
set DRIVE=
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
echo.