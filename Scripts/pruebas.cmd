@echo off
chcp 1252
color 47
mode con cols=80 lines=25
echo ==================================
echo =                                =
echo =  Ejemplo de colores para       =
echo =  consola de Administraci�n     =
echo =  Y las columnas y filas        =
echo =  del terminal cambiadas a      =
echo =            80 x 25             =
echo =                                =
echo ==================================
echo.
echo.
echo Pausa con mensaje personalizado, presiona una tecla para continuar.
pause>nul
color 0F
mode con cols=132 lines=45
set /p Nombre=Escriba su Nombre:
echo Tu nombre es "%Nombre%"
set /p numero1=Teclea un numero:
set /p numero2=Teclea otro numero:
set /a suma= %numero1% + %numero2%
echo La suma de %numero1% y %numero2% es :  %suma%
echo Otra pausa, presiona una tecla para mostrar un men�.
pause>nul
@echo off
:Menu
cls
color 0F
echo Seleccione su opci�n tecleando el n�mero respectivo.
echo.
echo 1. Primera Opci�n
echo 2. Segunda Opci�n
echo 3. Salir
@rem Recogida del valor del usuario y env�o a las distintas opciones
set /p var=
if %var%==1 goto :Primero
if %var%==2 goto :Segundo
if %var%==3 goto :Salida
if %var% GTR 3 echo Error
goto :Menu

@rem Aqui est�n las distintas opciones del men�
:Primero
cls 
color a
echo Esta es la Primera Opci�n
echo Presiona una tecla para volver al men�
pause>Nul
goto :Menu
:Segundo
cls 
color 1a
echo Esta es la Segunda Opci�n
echo Presiona una tecla para volver al  men�
pause>Nul
goto :Menu
:Salida
color 0F
