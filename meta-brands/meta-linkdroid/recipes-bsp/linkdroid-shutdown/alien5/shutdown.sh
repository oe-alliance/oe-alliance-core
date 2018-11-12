#!/bin/sh

sync ; sync ; sync
echo 0  > /sys/class/ampanel/set_brightness
echo 8888  > /sys/class/ampanel/data
echo 1  > /sys/class/ampanel/set_brightness
echo "standby" > /sys/power/state

rm /sbin/halt
rm /sbin/shutdown
rm /sbin/reboot
busybox ln -s /usr/bin/amlhalt /sbin/halt
busybox ln -s /usr/bin/amlhalt /sbin/shutdown
busybox ln -s /usr/bin/amlreboot /sbin/reboot

