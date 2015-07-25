#!/bin/sh

echo "0" > /sys/class/leds/wetek\:blue\:wifiled/brightness
echo "0" > /sys/class/leds/wetek\:blue\:ethled/brightness
echo "mem" > /sys/power/state
