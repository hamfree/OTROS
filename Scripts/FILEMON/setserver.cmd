@rem ************************************************************************************
@rem *                                                                                  *
@rem *    Script para elegir la version del servidor aplicaciones a ejecutar,           *
@rem *    personalizado para el portatil FILEMON:                                       *
@rem *    - Contenedor de servlets/JSP TOMCAT (En C:\des\bin\tomcat)                    *
@rem *    - Servidor J2EE TOMEE (En C:\des\bin\tomee)                                   *
@rem *                                                                                  *
@rem ************************************************************************************

@echo off
@chcp 1252 > nul

if not %COMPUTERNAME%==FILEMON (
    echo En este equipo de nombre '%COMPUTERNAME%' no se puede utilizar el script '%0'
    goto EquipoNoValido
)



@rem Variables para herramientas del entorno de desarrollo
set PATHDES=C:\des\bin

@rem Variable necesaria para la ejecución de Tomcat



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
echo CATALINA_HOME actual: 
echo %CATALINA_HOME%
echo.

:EquipoNoValido
@rem Eliminamos variables de entorno usadas internamente en el script
echo.