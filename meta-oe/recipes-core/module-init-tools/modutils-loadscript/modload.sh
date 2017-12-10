#! /bin/sh
### BEGIN INIT INFO
# Provides:          devprobe
# Required-Start:    kmod checkfs checkroot-bootclean
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Probe USB/HID/input modules
# Description:       Probes all USB/HID/input modules so that drivers load automatically
### END INIT INFO

grep -h '^usb:\|^input:\|^hid:' `find /sys/devices/ -name modalias` | while read m
do
	modprobe $m > /dev/null 2> /dev/null
done

exit 0
