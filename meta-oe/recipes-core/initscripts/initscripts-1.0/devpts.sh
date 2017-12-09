#! /bin/sh
### BEGIN INIT INFO
# Provides:          mountdevsubfs
# Required-Start:    mountkernfs
# Required-Stop:
# Should-Start:      udev
# Default-Start:     S
# Default-Stop:
# Short-Description: Mount special file systems under /dev.
# Description:       Mount the virtual filesystems the kernel provides
#                    that ordinarily live under the /dev filesystem.
### END INIT INFO
#
# This script gets called multiple times during boot
#

. /etc/default/devpts

if grep -q devpts /proc/filesystems
then
	#
	#	Create multiplexor device.
	#
	test -c /dev/ptmx || mknod -m 666 /dev/ptmx c 5 2

	#
	#	Mount /dev/pts if needed.
	#
	if ! grep -q devpts /proc/mounts
	then
		mkdir -p /dev/pts
		mount -t devpts devpts /dev/pts -ogid=${TTYGRP},mode=${TTYMODE}
	fi
fi
