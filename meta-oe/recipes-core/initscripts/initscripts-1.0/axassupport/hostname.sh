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
	if [ "$(cat /proc/stb/info/boxtype)" == 'odinm7' ]; then
		echo "classm" > /etc/hostname
	fi
	hostname -F /etc/hostname
fi
