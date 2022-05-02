@echo off
SETLOCAL ENABLEEXTENSIONS
echo.
echo.
echo Creacion de la estructura estandar de directorios de Maven. 
echo Pulse cualquier tecla para continuar.
pause>nul
if not exist ".\src\." (
    md src\main\java
    md src\main\resources
    md src\main\filters
    md src\test\java
    md src\test\resources
    md src\test\filters
    
) else (
    echo "Ya existe el directorio src."
    if not exist ".\src\main\." (
        md src\main\java
        md src\main\resources
        md src\main\filters

    ) else (
        echo "Ya existe el directorio src\main."
        if not exist ".\src\main\java\." (
            md src\main\java
        ) else (
            echo "Ya existe el directorio src\main\java."
        )
        if not exist ".\src\main\resources\." (
            md src\main\resources
        ) else (
            echo "Ya existe el directorio src\main\resources."
        )
        if not exist ".\src\main\filters\." (
            md src\main\java\filters
        ) else (
            echo "Ya existe el directorio src\main\filters."
        )
    )

    if not exist ".\src\test\." (
        md src\test\java
        md src\test\resources
        md src\test\filters

    ) else (
        echo "Ya existe el directorio src\test."
        if not exist ".\src\test\java\." (
            md src\test\java
        ) else (
            echo "Ya existe el directorio src\test\java."
        )
        if not exist ".\src\test\resources\." (
            md src\test\resources
        ) else (
            echo "Ya existe el directorio src\test\resources."
        )
        if not exist ".\src\test\filters\." (
            md src\test\java\filters
        ) else (
            echo "Ya existe el directorio src\test\filters."
        )
    )

)
copy nul LICENSE.txt > nul
copy nul NOTICE.txt > nul
copy nul README.txt > nul

tree .
echo "Estructura de directorios estandar de Maven generada."
