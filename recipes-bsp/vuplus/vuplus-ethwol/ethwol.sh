#! /bin/sh
#
# Set eth0 WOL 
#
# Version:      @(#)ethwol  1.00  18-Jul-2013
#

PATH=/sbin:/bin:/usr/sbin:/usr/bin

#activate WakeOnLAN
grep -q 'enable' /proc/stb/fp/wol
if [ $? -eq 0 ]
then
	echo [WOL] activate WakeOnLAN at device eth0
	ethtool -s eth0 wol g
else
	echo [WOL] WakeOnLAN is not enabled
fi

: exit 0
