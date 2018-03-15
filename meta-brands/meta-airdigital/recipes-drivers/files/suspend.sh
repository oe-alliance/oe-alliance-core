#!/bin/sh

if [ "$1x" == "stopx" ];then
    exit 0
fi

mount -t sysfs sys /sys
/usr/bin/turnoff_power
