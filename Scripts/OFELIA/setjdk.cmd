@rem 
@rem Script para elegir version del JDK, personalizado para el portatil OFELIA     
@rem Configura el PATH del sistema colocando por orden de importancia las rutas    
@rem a las aplicaciones del sistema operativo, agregando luego las rutas de las    
@rem siguientes aplicaciones:                                                      
@rem - Bitvise SSH Client                                                          
@rem - Calibre2                                                                    
@rem - GnuPG                                                                       
@rem - ZeroTier                                                                    
@rem - MySQL                                                                       
@rem Despues de agregar todas las rutas del sistema y aplicaciones antes           
@rem indicadas, se construyen las rutas para las herramientas de desarrollo        
@rem y servidores de aplicaciones, que se encuentran todos instalados bajo         
@rem la ruta "C:\des\bin". Las aplicaciones cuyas rutas se agregan al path         
@rem son:                                                                          
@rem - Ant                                                                         
@rem - Derby                                                                       
@rem - OpenSSL                                                                     
@rem - Git                                                                         
@rem - Servidor J2EE Tomee                                                         
@rem - Servidor Glassfish 5                                                        
@rem - Servidor Glassfish 6                                                        
@rem - OpenSSL                                                                     
@rem - Lenguaje de programacion Java (versiones 1.8, 11, 17)                       
@rem - Directorio con scripts                                                      
@rem - Directorio con utilidades                                                   
@rem - Maven                                                                       
@rem                                                                               
@rem                                                                               
@rem Historia:                      
@rem - 2022/05/07 - Dependiendo del JDK elegido se fijara la ruta de Glassfish 5 o 
@rem                Glassfish 6.
@rem - 2022/05/07 - Se agrega la ruta del directorio de utilidades.                                  
@rem - 2022/04/02 - Copiado originalmente de la versión para FILEMON, y modificado 
@rem                con las características y programas instalados en el nuevo     
@rem                portátil                                                       
@rem                                                                               
@rem 

@echo off
@chcp 1252 > nul

if not %COMPUTERNAME%==OFELIA (
    echo En este equipo de nombre '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)

@rem El disco de desarrollo en OFELIA es la unidad C:
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
echo      3. JDK 17
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
if %var%==3 goto :jdk17
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
set jdkactivado=JDK_8
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
set jdkactivado=JDK_11
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%DRIVE%\des\bin\jdk11
echo Estableciendo PATH 
echo Version de java que se activara:
%JAVA_HOME%\bin\java -XshowSettings:vm -version
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu

@rem Entorno de desarrollo JDK 17
:jdk17
cls 
set jdkactivado=JDK_17
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%DRIVE%\des\bin\jdk17
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
set PATHSYS=%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\system32\Wbem
set PATHSYS=%PATHSYS%;%SystemRoot%\system32\WindowsPowerShell\v1.0
set PATHSYS=%PATHSYS%;%SystemRoot%\system32\OpenSSH
set PATHSYS=%PATHSYS%;%WINDOWSAPPS%
set PATHSYS=%PATHSYS%;%POWERSHELL%

@rem El resto de rutas de otras aplicaciones incluidas en el path del sistema
set PATHSYS=%PATHSYS%;%ProgramFiles%\Calibre2
set PATHSYS=%PATHSYS%;%ProgramFiles(x86)%\gnupg\bin
set PATHSYS=%PATHSYS%;%ProgramFiles(x86)%\Bitvise SSH Client
set PATHSYS=%PATHSYS%;%ProgramFiles%\dotnet\
set PATHSYS=%PATHSYS%;%ProgramFiles%\Docker\Docker\resources\bin
set PATHSYS=%PATHSYS%;%ProgramData%\DockerDesktop\version-bin
set PATHSYS=%PATHSYS%;%ProgramFiles%\NVIDIA Corporation\NVIDIA NvDLISR

@rem Variables para herramientas del entorno de desarrollo
set PATHDES=%DRIVE%\des\bin

@rem Aqui van las rutas a los binarios de las herramientas de desarrollo.
set ANT=%PATHDES%\ant\bin
set DERBY=%PATHDES%\derby\bin
set GIT=%PATHDES%\git\bin

@rem Con el JDK8 usamos Glassfish 5 y con JDK 11 y 17 Glass
if %jdkactivado%==JDK_8 (
    echo Con el JDK 8 Glassfish se ejecutara en version 5
    set GLASSFISH=%PATHDES%\glassfish5\bin;%PATHDES%\glassfish5\glassfish\bin
) else (
    echo Con el %jdkactivado% Glassfish se ejecutara en version 6
    set GLASSFISH=%PATHDES%\glassfish6\bin;%PATHDES%\glassfish6\glassfish\bin
)



set MVN=%PATHDES%\mvn\bin
set MYSQL=%PATHDES%\mysql\bin
set OPENSSL=%PATHDES%\openssl\bin
set TOMEE=%PATHDES%\tomee\bin
set SCRIPT=%PATHDES%\scripts
set UTIL=%PATHDES%\utiles


@rem Variable necesaria para la ejecucion de Tomee
set CATALINA_HOME=%DRIVE%\des\bin\tomee

@rem Variable para la base de datos Derby
set DERBY_HOME=%DRIVE%\des\bin\derby\lib

@rem Agregamos la ruta a los ejecutables del entorno de Java elegido
set PATHDES=%PATHDES%;%JAVA_HOME%\bin

@rem Componemos el path PATHDES de las herramientas de desarrollo.
set PATHDES=%PATHDES%;%ANT%
set PATHDES=%PATHDES%;%DERBY%
set PATHDES=%PATHDES%;%GLASSFISH%
set PATHDES=%PATHDES%;%GIT%
set PATHDES=%PATHDES%;%MVN%
set PATHDES=%PATHDES%;%MYSQL%
set PATHDES=%PATHDES%;%OPENSSL%
set PATHDES=%PATHDES%;%TOMEE%
set PATHDES=%PATHDES%;%SCRIPT%
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
set DERBY=
set DRIVE=
set GIT=
set GLASSFISH=
set jdkactivado=
set MVN=
set MYSQL=
set OPENSSL=
set PATHSYS=
set PATHDES=
set POWERSHELL=
set SCRIPT=
set UTIL=
set TOMEE=
set var=
set WINDOWSAPPS=

echo.