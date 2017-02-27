#!/bin/sh

sync ; sync ; sync
echo "0" > /sys/class/leds/blue\:heartbeat/brightness
echo "standby" > /sys/power/state

