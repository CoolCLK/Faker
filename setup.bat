@echo off
echo Start to setup...
.\gradlew.bat setupDecompWorkspace
.\gradlew.bat genIntellijRuns
echo Done
@echo on