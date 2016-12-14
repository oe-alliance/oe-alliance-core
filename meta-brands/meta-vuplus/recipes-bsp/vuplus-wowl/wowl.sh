#! /bin/sh
#
# wowl
#
# Version:      @(#)wowl  1.00  10-Jul-2016  
#

PATH=/sbin:/bin:/usr/sbin:/usr/bin

#activate WOWL
grep -q 'enable' /proc/stb/fp/wol
if [ $? -eq 0 ]
then
	echo [WOWL] Activating WOWL...
	wl wowl 1
	wl wowl_activate
	mknod /dev/wake0 c 34 0
	echo enable > /proc/stb/fp/forcewol
	sleep	1
else
	echo [WOL] WakeOnLAN is not enabled
fi


: exit 0

