#!/bin/sh

echo "libreader start!!!"
mount /dev/mmcblk0p3 /boot/
/usr/bin/libreader 720P_50
sleep 2
