@echo off
cd /d %windir%\system32
net start wuauserv
start /wait wuapp.exe