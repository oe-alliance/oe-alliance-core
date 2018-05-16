#!/bin/sh

sync ; sync ; sync
echo 0  > /sys/class/ampanel/set_brightness
echo 8888  > /sys/class/ampanel/data
echo 1  > /sys/class/ampanel/set_brightness
echo "standby" > /sys/power/state

