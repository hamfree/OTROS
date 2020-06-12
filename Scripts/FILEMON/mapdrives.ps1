$i=3
while($True){
    $error.clear()
    $MappedDrives = Get-SmbMapping |Where-Object -property Status -Value Unavailable -EQ | Select-Object LocalPath,RemotePath
    foreach( $MappedDrive in $MappedDrives)
    {
        try {
            New-SmbMapping -LocalPath $MappedDrive.LocalPath -RemotePath $MappedDrive.RemotePath -Persistent $True
        } catch {
            Write-Host "Hubo un error mapeando $MappedDrive.RemotePath a $MappedDrive.LocalPath"
        }
    }
    $i = $i - 1
    if($error.Count -eq 0 -Or $i -eq 0) {break}

    Start-Sleep -Seconds 30

}