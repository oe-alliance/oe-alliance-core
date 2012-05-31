#!/bin/sh
if ! [ -f '/etc/enigma2/settings' ]; then
	for x in `cat /proc/mounts | cut -d ' ' -f 2 | grep '^/media'`; do
		path=`echo $x'/usr/lib/opkg'`
		if [ -x $path ]; then
			rm -fR $path
		fi
	done
fi
