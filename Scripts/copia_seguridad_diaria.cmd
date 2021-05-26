echo off
echo "Creando carpeta..."
 
set dia_actual=%date:~-4,4%%date:~3,2%%date:~-10,2%
set destino_backup=E:\backup\%dia_actual%
set origen_backup="D:\Mis documentos\Empresa"
 
mkdir %destino_backup%
 
echo "Copiando ficheros..."
 
robocopy %origen_backup% %destino_backup% /S /E /R:0 /COPYALL /SECFIX /NP /Log:%destino_backup%\%dia_actual%.log /NJS
if errorlevel 4 echo Error en la copia &amp; goto Copia_Error
if errorlevel 1 echo Copia finalizada correctamente &amp; goto Copia_Correcta
if errorlevel 0 echo Copia finalizada correctamente sin cambios &amp; goto Copia_Correcta_Sin_Cambios
 
:Copia_Error
msg * Se ha producido un error en la copia.
pause
exit
 
:Copia_Correcta
msg * Copia realizada correctamente.
pause
exit
 
:Copia_Correcta_Sin_Cambios
msg * Copia realizada correctamente sin cambios.
pause
exit