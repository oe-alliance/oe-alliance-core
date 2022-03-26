#!/bin/sh
echo "libreader start!!!"
mount /dev/mtdblock2 /boot/
/usr/bin/libreader 720P_50
sleep 2
