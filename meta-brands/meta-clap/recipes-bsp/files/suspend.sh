#!/bin/sh

runlevel=runlevel | cut -d' ' -f2

if [ "$runlevel" != "0" ] ; then
then
	exit 0
fi

mount -t sysfs sys /sys

/usr/bin/hihalt
