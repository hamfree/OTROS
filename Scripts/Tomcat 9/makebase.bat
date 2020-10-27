@echo off

rem Este script crea la estructura de directorios requerida para ejecutar Tomcat
rem en un directorio separado apuntando %CATALINA_BASE% a el. Copia el 
rem directorio conf desde %CATALINA_HOME%, y crea directorios vacios par 
rem bin, lib, logs, temp, webapps, y work.
rem
rem Si el fichero %CATALINA_HOME%/bin/setenv.sh existe entonces es copiado al 
rem directorio destino tambien.
rem
rem Uso: makebase <ruta-al-directorio-destino> [-w | --webapps]

setlocal

rem Adivina CATALINA_HOME si no esta definida
set "CURRENT_DIR=%cd%"
if not "%CATALINA_HOME%" == "" goto gotHome
set "CATALINA_HOME=%CURRENT_DIR%"
if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
cd ..
set "CATALINA_HOME=%cd%"
cd "%CURRENT_DIR%"
:gotHome

if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
echo La variable de entorno CATALINA_HOME no esta definida correctamente
echo Esta variable de entorno se necesita para ejecutar este programa
goto EOF
:okHome

rem el primer argumento es el directorio destino
set BASE_TGT=%1

if %BASE_TGT%.==. (
    rem no se ha indicado el directorio destino; salimos
    echo Uso: makebase ^<ruta-al-directorio-destino^>
    goto :EOF
)

set COPY_WEBAPPS=false

rem analiza los argumentos
for %%a in (%*) do (
   if "%%~a"=="--webapps" (
       set COPY_WEBAPPS=true
   )
   if "%%~a"=="-w" (
       set COPY_WEBAPPS=true
   )
)

if exist %BASE_TGT% (
  rem el directorio destino existe
  echo El directorio destino existe

    rem salimos si el directorio destino no esta vacio
    for /F %%i in ('dir /b %BASE_TGT%\*.*') do (
        echo El directorio destino no esta vacio
        goto :EOF
    )
) else (
    rem crea el directorio destino
    mkdir %BASE_TGT%
)

rem crea los directorios vacios
for %%d in (bin, conf, lib, logs, temp, webapps, work) do (
    mkdir %BASE_TGT%\%%d
)

if "%COPY_WEBAPPS%" == "true" (
    echo Copiando las aplicaciones web
    robocopy %CATALINA_HOME%\webapps %BASE_TGT%\webapps /E > nul
    rem copia el directorio conf recursivamente
    robocopy %CATALINA_HOME%\conf %BASE_TGT%\conf /E > nul
) else (
    rem copia el directorio conf sin los subdirectorios y suprime los avisos
    robocopy %CATALINA_HOME%\conf %BASE_TGT%\conf > nul
    rem crea el directorio ROOT vacio
    mkdir %BASE_TGT%\webapps\ROOT
)

rem copia setenv.bat si existe
robocopy %CATALINA_HOME%\bin %BASE_TGT%\bin setenv.bat > nul

echo Se creo el directorio CATALINA_BASE en %BASE_TGT%

echo.
echo Puede lanzar la nueva instancia ejecutando:
echo     set CATALINA_HOME=%CATALINA_HOME%
echo     set CATALINA_BASE=%BASE_TGT%
echo     %%CATALINA_HOME%%/bin/catalina.bat run

echo.
echo Atención: Los puertos en conf\server.xml podrian ser utilizados por una 
echo     instancia diferente. Por favor, revise sus ficheros de configuracion
echo     y actualizelos donde sea necesario.
echo.

:EOF
