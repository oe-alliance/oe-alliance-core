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
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-9000ru' ]; then
		echo "hdx-marvel" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-5000ru' ]; then
		echo "hdx-5000" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-1000ru' ]; then
		echo "hde-1000" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-5000sv' ]; then
		echo "mbtwin" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-1000sv' ]; then
		echo "mbmini" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-1000de' ]; then
		echo "xpeedlx" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-9000de' ]; then
		echo "xpeedlx3" > /etc/hostname
	fi	
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-7012' ]; then
		echo "unibox-hd3" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-7000' ]; then
		echo "unibox-hd3" > /etc/hostname
	fi		
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-5000' ]; then
		echo "unibox-hd2" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-3000' ]; then
		echo "unibox-hd1" > /etc/hostname
	fi
	if [ "$(cat /proc/stb/info/boxtype)" == 'ini-1000' ]; then
		echo "unibox-hde" > /etc/hostname
	fi
	hostname -F /etc/hostname
fi
