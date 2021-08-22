#!/bin/sh

echo extend rootfs to max size
resize2fs /dev/mmcblk0p16

echo "libreader start!!!"
mount /dev/mmcblk0p3 /boot/
/usr/bin/libreader 720P_50
sleep 2
