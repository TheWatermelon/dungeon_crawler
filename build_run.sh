#!/bin/bash
# Build and run dungeon crawler
# Author : TheWatermelon

project_path=$HOME/dungeon_crawler/

/usr/bin/ant compile
ln -s $project_path"saves" $project_path"bin/saves"
cd bin && java game/Main
cd ..


