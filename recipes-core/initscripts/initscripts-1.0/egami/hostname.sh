#!/bin/sh
### BEGIN INIT INFO
# Provides:          hostname
# Required-Start:
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Set hostname based on /etc/hostname
### END INIT INFO

if test -f /etc/hostname
then
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-5000ru' ]; then
		echo "hdx-5000" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-1000ru' ]; then
		echo "hdx-1000" > /etc/hostname
	fi	
	hostname -F /etc/hostname
fi
