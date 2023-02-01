$remoteComputerName = $args[0]
$session = New-PSSession -ComputerName $remoteComputerName
Invoke-Command -Session $session -ScriptBlock {
Start-Process -FilePath "powershell.exe" -Verb RunAs -ArgumentList "-NoProfile -ExecutionPolicy Bypass -Command "Get-WindowsUpdate -Install""
}