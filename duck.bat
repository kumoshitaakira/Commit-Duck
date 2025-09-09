@echo off
setlocal enabledelayedexpansion

set REPO_DIR=%~dp0
set REPO_DIR=%REPO_DIR:~0,-1%
set OUT_DIR=%REPO_DIR%\out

if "%1"=="install" goto :install
if "%1"=="status" goto :status
if "%1"=="refresh" goto :refresh
if "%1"=="help" goto :help

echo duck install ^| status ^| refresh ^| help
goto :eof

:build
if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"
javac -d "%OUT_DIR%" "%REPO_DIR%\src\duck\*.java"
exit /b 0

:install
call :build
set HOOK_DIR=%REPO_DIR%\.git\hooks
if not exist "%HOOK_DIR%" mkdir "%HOOK_DIR%"
> "%HOOK_DIR%\post-commit.bat" echo @echo off
>>"%HOOK_DIR%\post-commit.bat" echo for /f "delims=" %%%%a in ('git rev-parse --show-toplevel 2^>nul') do set REPO_ROOT=%%%%a
>>"%HOOK_DIR%\post-commit.bat" echo if exist "%%REPO_ROOT%%\duck.bat" call "%%REPO_ROOT%%\duck.bat" refresh ^>nul 2^>^&1
echo Installed .git\hooks\post-commit.bat
echo Done.
goto :eof

:status
if not exist "%OUT_DIR%" call :build
java -cp "%OUT_DIR%" duck.DuckCli status
goto :eof

:refresh
if not exist "%OUT_DIR%" call :build
java -cp "%OUT_DIR%" duck.DuckCli refresh
goto :eof

:help
echo duck install ^| status ^| refresh ^| help
