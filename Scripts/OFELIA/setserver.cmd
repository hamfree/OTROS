@rem ************************************************************************************
@rem *                                                                                  *
@rem *    Script para elegir la version del servidor aplicaciones a ejecutar,           *
@rem *    personalizado para el portatil FILEMON:                                       *
@rem *    - Contenedor de servlets/JSP TOMCAT (En C:\des\bin\tomcat)                    *
@rem *    - Servidor J2EE TOMEE (En C:\des\bin\tomee)                                   *
@rem *                                                                                  *
@rem *    2022/02/19 - Modifico el script para que, aparte de seleccionar el            *
@rem *                 el servidor de aplicaciones que arrancarán los comandos          *
@rem *                 'catalina.bat' y 'startup.bat' modifique la ruta del PATH        *
@rem *                 para que se ejecuten los scripts del servidor de aplicaciones    *
@rem *                 seleccionado. Ya no uso la aplicación 'Visual Studio Code' y la  *
@rem *                 quito de la ruta del PATH.                                       *
@rem *    2021/05/26 - Actualmente no se usa este script porque solo está               *
@rem *                 instalado el servidor de aplicaciones TOMEE                      *
@rem *                                                                                  *
@rem ************************************************************************************

@echo off
@chcp 1252 > nul

if not %COMPUTERNAME%==OFELIA (
    echo En este equipo de nombre '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)

@rem El disco de desarrollo en OFELIA es la unidad C:
set DRIVE=C:

@rem Variables para herramientas del entorno de desarrollo

set PATHDES=%DRIVE%\des\bin

:Menu
cls
echo.
echo SETSERVER.CMD - Permite al usuario seleccionar un servidor de aplicaciones 
echo para usar de los instalados en el sistema
echo.
echo   Seleccione un servidor de aplicaciones indicando su numero en el menu
echo.
echo.
echo      1. Contenedor de Servlets/JSP TOMCAT
echo      2. Servidor J2EE TOMEE
echo      0. SALIR
echo.
echo   CATALINA_HOME actual: %CATALINA_HOME%
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

:salida
@rem A la salida reiniciamos el PATH del sistema....
set PATH=
@rem Las rutas estándar de Windows
set SISTEMA=%SystemRoot%\system32
set SISTEMA=%SISTEMA%;%SystemRoot%
set SISTEMA=%SISTEMA%;%SystemRoot%\system32\Wbem
set SISTEMA=%SISTEMA%;%SystemRoot%\system32\OpenSSH


@rem Las apps de windows instaladas por el usuario y sus scripts de powershell
set WINDOWSAPPS=%USERPROFILE%\AppData\Local\Microsoft\WindowsApps
set POWERSHELL=%USERPROFILE%\Documents\WindowsPowerShell\Scripts

@rem En el path del sistema las rutas mas importantes primero
set PATHSYS=%SISTEMA%
set PATHSYS=%PATHSYS%;%WINDOWSAPPS%
set PATHSYS=%PATHSYS%;%POWERSHELL%

@rem El resto de rutas de otras aplicaciones incluidas en el path del sistema

set BITVISE=%ProgramFiles(x86)%\Bitvise SSH Client
set CALIBRE=%ProgramFiles%\Calibre2
SET CHOCO=%ProgramData%\chocolatey\bin
set DOTNET=%ProgramFiles%\dotnet
set GPG=%ProgramFiles(x86)%\gnupg\bin
set NVIDIA=%ProgramFiles%\NVIDIA Corporation\NVIDIA NvDLISR
set ZT=%ProgramFiles(x86)%\ZeroTier\One

set PATHSYS=%PATHSYS%;%BITVISE%
set PATHSYS=%PATHSYS%;%CALIBRE%
set PATHSYS=%PATHSYS%;%CHOCO%
set PATHSYS=%PATHSYS%;%DOTNET%
set PATHSYS=%PATHSYS%;%GPG%
set PATHSYS=%PATHSYS%;%NVIDIA%
set PATHSYS=%PATHSYS%;%ZT%

@rem Variables para herramientas del entorno de desarrollo
set PATHDES=%DRIVE%\des\bin

@rem Aqui van las rutas a los binarios de las herramientas de desarrollo.
set ANT=%PATHDES%\ant\bin
set DERBY=%PATHDES%\derby\bin
set GIT=%PATHDES%\git\bin;%PATHDES%\git\cmd

@rem Con el JDK8 usamos Glassfish 5 y con los JDKs 11, 17 y 19 Glassfish 6
if %JAVA_HOME%==C:\des\bin\jdk8 (
    echo Con el JDK 8 Glassfish se ejecutara en version 5
	set MSGGLASSFISH=Glassfish 5 activado
    set GLASSFISH=%PATHDES%\glassfish5\bin;%PATHDES%\glassfish5\glassfish\bin
) else (
    echo Con versiones superiores del JDK Glassfish se ejecutara en version 6
	set MSGGLASSFISH=Glassfish 6 activado
    set GLASSFISH=%PATHDES%\glassfish6\bin;%PATHDES%\glassfish6\glassfish\bin
)

set MVN=%PATHDES%\mvn\bin
set MYSQL=%PATHDES%\mysql\bin
set NODE=%PATHDES%\node
set NPM=%USERPROFILE%\AppData\Roaming\npm
set OPENSSL=%PATHDES%\openssl\bin
set PYTHON=%PATHDES%\python\Scripts;%PATHDES%\python
set SCRIPT=%PATHDES%\scripts
set SYSINT=%PATHDES%\sysint
set UTIL=%PATHDES%\utiles
set WILDFLY=%PATHDES%\wildfly\bin

@rem Variable para la base de datos Derby
set DERBY_HOME=%DRIVE%\des\bin\derby\lib

@rem Agregamos la ruta a los ejecutables del entorno de Java actual.
set PATHDES=%PATHDES%;%JAVA_HOME%\bin

@rem Componemos el path PATHDES de las herramientas de desarrollo.
@rem El orden de rutas sigue el orden alfabético de las variables de entorno.
set PATHDES=%PATHDES%;%ANT%
set PATHDES=%PATHDES%;%CATALINA_HOME%\bin
set PATHDES=%PATHDES%;%DERBY%
set PATHDES=%PATHDES%;%GLASSFISH%
set PATHDES=%PATHDES%;%GIT%
set PATHDES=%PATHDES%;%MVN%
set PATHDES=%PATHDES%;%MYSQL%
set PATHDES=%PATHDES%;%NODE%
set PATHDES=%PATHDES%;%NPM%
set PATHDES=%PATHDES%;%OPENSSL%
set PATHDES=%PATHDES%;%PYTHON%
set PATHDES=%PATHDES%;%SCRIPT%
set PATHDES=%PATHDES%;%SYSINT%
set PATHDES=%PATHDES%;%UTIL%
set PATHDES=%PATHDES%;%WILDFLY%

echo.
echo Pulse una tecla para componer el PATH del sistema...
pause>Nul

@rem Componemos la variable del sistema PATH
set PATH=
set PATH=%PATHSYS%;%PATHDES%

cls
echo Equipo actual: %COMPUTERNAME%
echo PATH actual:
echo %PATH%
echo JAVA SDK:
java --version
echo "Servidor Tomcat | TomEE.:" %CATALINA_HOME%
echo "Servidor Glassfish......:" %MSGGLASSFISH%
echo "Servidor WildFly........:" %WILDFLY%
echo.

:EquipoNoValido
@rem Eliminamos variables de entorno usadas internamente en el script
set ANT=
set DERBY=
set DRIVE=
set GIT=
set GLASSFISH=
set GPG=
set MVN=
set MYSQL=
set NODE=
set NPM=
set OPENSSL=
set PATHSYS=
set PATHDES=
set POWERSHELL=
set PYTHON=
set SCRIPT=
set SISTEMA=
set SYSINT=
set UTIL=
set var=
set WILDFLY=
set WINDOWSAPPS=

echo.