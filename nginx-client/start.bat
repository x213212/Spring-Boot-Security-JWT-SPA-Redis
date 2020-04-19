@echo off
cd /d "%~dp0"
start  .\nginx.exe
tasklist /fi "imagename eq nginx.exe"
echo 'im client01'
exit

