#!/bin/sh
ubiattach /dev/ubi_ctrl -m 2
if [ -e /dev/ubi1_0 ]; then
	if  [ ! -e /media/backup ]; then
		mkdir /media/backup
	fi	
	mount -t ubifs /dev/ubi1_0 /media/backup
else
	ubiformat /dev/mtd2 -y
	ubiattach /dev/ubi_ctrl -m 2
	ubimkvol /dev/ubi1 -N backup -s 7MiB
	if  [ ! -e /media/backup ]; then
		mkdir /media/backup
	fi
	mount -t ubifs /dev/ubi1_0 /media/backup
fi	
