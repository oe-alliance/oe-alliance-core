#!/bin/sh

runlevel=runlevel | cut -d' ' -f2

if [ "$runlevel" != "0" ] ; then
	exit 0
fi

mount -t proc proc /proc
mount -t sysfs sys /sys

/usr/bin/turnoff_power
