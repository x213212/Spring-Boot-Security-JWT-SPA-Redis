@echo off
cd /d "%~dp0"
start .\nginx.exe
tasklist /fi "imagename eq nginx.exe"
echo 'im master'
:tx

tasklist /fi "imagename eq nginx.exe"
@ping 127.0.0.1 -n 2 -w 1000 > nul
cls
goto tx