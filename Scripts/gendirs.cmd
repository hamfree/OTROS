@echo off
chcp 1252
color 47
mode con cols=80 lines=25
echo.
echo.
echo Creacion de la estructura estandar de directorios de Maven. 
echo Pulse cualquier tecla para continuar.
pause>nul
md src
md src\main
md src\main\java
md src\main\resources
md src\main\filters
md src\test
md src\test\java
md src\test\resources
md src\test\filters
md src\it
md src\assembly
md src\site
echo "" > LICENSE.txt
echo "" > NOTICE.txt
echo "" > README.txt

