@rem 
@rem Script para elegir version del JDK, personalizado para el portatil OFELIA     
@rem Configura el PATH del sistema colocando por orden de importancia las rutas    
@rem a las aplicaciones del sistema operativo, agregando luego las rutas de las    
@rem siguientes aplicaciones:                                                      
@rem - Bitvise SSH Client                                                          
@rem - Calibre2                                                                    
@rem - Chocolatey
@rem - dotNet
@rem - GnuPG                                                                       
@rem - NVidia
@rem - ZeroTier                                                                    
@rem Despues de agregar todas las rutas del sistema y aplicaciones antes           
@rem indicadas, se construyen las rutas para las herramientas de desarrollo        
@rem y servidores de aplicaciones, que se encuentran todos instalados bajo         
@rem la ruta "C:\des\bin". Las aplicaciones cuyas rutas se agregan al path         
@rem son:                                                                          
@rem - Ant   
@rem - Derby                                                                       
@rem - Directorio con scripts                                                      
@rem - Directorio con utilidades                                                   
@rem - Directorio con utilidades de SysInternals
@rem - Git                                                                         
@rem - Maven                                                                       
@rem - MySQL
@rem - Node
@rem - npm (Node)
@rem - OpenSSL                              
@rem - Python                                       
@rem - SDK Java (versiones 1.8, 11, 17 y 19)                       
@rem - Servidor J2EE Tomee                                                         
@rem - Servidor J2EE Glassfish 5                                                        
@rem - Servidor J2EE Glassfish 6                    
@rem - Servidor Wildfly                                    
@rem                                                                               
@rem                                                                               
@rem Historia:
@rem - 2023/02/19 - Se elimina del PATH la aplicación 'Visual Studio Code' porque 
@rem                la desinstalé y ya no uso. Ahora uso Notepad++ que ocupa menos 
@rem                espacio y para mí es suficiente. Se agrega el servidor de 
@rem                aplicaciones Wildfly.
@rem - 2022/11/04 - Se corrige la construcción del PATH después de una actualización
@rem                de los SDKs de JAVA, el cambio de ruta de OpenSSL y la 
@rem                instalación de otras utilidades.
@rem - 2022/10/10 - Se agregan mas rutas a los dos tipos de PATH. Se agrega el JDK 19.
@rem                La construcción del PATH de sistema ahora se realiza igual que el 
@rem                de desarrollo.
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
echo      4. JDK 19
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
if %var%==4 goto :jdk19
if %var%==0 goto :salida
if %var% GTR 4 goto :Error
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

@rem Entorno de desarrollo JDK 19
:jdk19
cls 
set jdkactivado=JDK_19
echo Estableciendo JAVA_HOME 
set JAVA_HOME=%DRIVE%\des\bin\jdk19
echo Estableciendo PATH 
echo Version de java que se activara: 
%JAVA_HOME%\bin\java -XshowSettings:vm -version
echo Presiona una tecla para volver al menu
pause > Nul
goto :Menu


@rem A la salida reiniciamos el PATH del sistema....
:salida

cls
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
set CODE=%PATHDES%\code
set DERBY=%PATHDES%\derby\bin
set GIT=%PATHDES%\git\bin;%PATHDES%\git\cmd

@rem Con el JDK8 usamos Glassfish 5 y con los JDKs 11, 17 y 19 Glassfish 6
if %jdkactivado%==JDK_8 (
    echo Con el JDK 8 Glassfish se ejecutara en version 5
    set GLASSFISH=%PATHDES%\glassfish5\bin;%PATHDES%\glassfish5\glassfish\bin
) else (
    echo Con el %jdkactivado% Glassfish se ejecutara en version 6
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
set TOMEE=%PATHDES%\tomee\bin
set UTIL=%PATHDES%\utiles
set WILDFLY=%PATHDES%\wildfly\bin


@rem Borramos el valor de CATALINA_HOME. Este valor se fija mediante el 
@rem script 'setserver.cmd' para poder elegir entre Tomcat o TomEE.
set CATALINA_HOME=

@rem Variable para la base de datos Derby
set DERBY_HOME=%DRIVE%\des\bin\derby\lib

@rem Agregamos la ruta a los ejecutables del entorno de Java elegido
set PATHDES=%PATHDES%;%JAVA_HOME%\bin

@rem Componemos el path PATHDES de las herramientas de desarrollo.
@rem El orden de rutas sigue el orden alfabético de las variables de entorno.
set PATHDES=%PATHDES%;%ANT%
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
set PATHDES=%PATHDES%;%TOMEE%
set PATHDES=%PATHDES%;%UTIL%
set PATHDES=%PATHDES%;%WILDFLY%

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
set CODE=
set DERBY=
set DRIVE=
set GIT=
set GLASSFISH=
set GPG=
set jdkactivado=
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
set TOMEE=
set var=
set WINDOWSAPPS=
set WILDFLY=

echo.