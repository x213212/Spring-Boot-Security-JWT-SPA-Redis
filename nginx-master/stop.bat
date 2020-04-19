taskkill /f /im "imagename eq nginx.exe"
nginx -s stop
nginx -s quit
tasklist /fi "imagename eq nginx.exe"
pause