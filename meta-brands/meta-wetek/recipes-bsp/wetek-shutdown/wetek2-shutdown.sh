#!/bin/sh

sync ; sync ; sync
echo "0" > /sys/class/leds/wetek\:blue\:wifiled/brightness
echo "0" > /sys/class/leds/wetek\:blue\:ethled/brightness
echo "standby" > /sys/power/state

