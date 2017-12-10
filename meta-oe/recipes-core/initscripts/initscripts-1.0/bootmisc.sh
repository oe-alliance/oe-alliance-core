#!/bin/sh
### BEGIN INIT INFO
# Provides:          bootmisc
# Required-Start:    $remote_fs
# Required-Stop:
# Should-Start:      udev
# Default-Start:     S
# Default-Stop:
# Short-Description: Miscellaneous things to be done during bootup.
# Description:       Some cleanup.  Note, it need to run after mountnfs-bootclean.sh.
### END INIT INFO

. /etc/default/rcS
#
# Put a nologin file in /etc to prevent people from logging in before
# system startup is complete.
#
if test "$DELAYLOGIN" = yes
then
  echo "System bootup in progress - please wait" > /etc/nologin
  cp /etc/nologin /etc/nologin.boot
fi

#
# Set pseudo-terminal access permissions.
#
if test -c /dev/ttyp0
then
	chmod 666 /dev/tty[p-za-e][0-9a-f]
	chown root:tty /dev/tty[p-za-e][0-9a-f]
fi

#
# Apply /proc settings if defined
#
SYSCTL_CONF="/etc/sysctl.conf"
if [ -f "${SYSCTL_CONF}" ]
then
	if [ -x "/sbin/sysctl" ]
	then
		/sbin/sysctl -p "${SYSCTL_CONF}"
	else
		echo "To have ${SYSCTL_CONF} applied during boot, install package <procps>."
	fi
fi

#
# Update /etc/motd.
#
if test "$EDITMOTD" != no
then
	uname -a > /etc/motd.tmp
	sed 1d /etc/motd >> /etc/motd.tmp
	mv /etc/motd.tmp /etc/motd
fi

#
# This is as good a place as any for a sanity check
# /tmp should be a symlink to /var/tmp to cut down on the number
# of mounted ramdisks.
if test ! -L /tmp && test -d /var/tmp
then
	rm -rf /tmp
	ln -sf /var/tmp/ /tmp
fi

#
# Update dynamic library cache, but only if ld.so.conf is present
#
if [ -e /etc/ld.so.conf ] ; then
	/sbin/ldconfig
fi

#
# Autostart Softcams (PLi Softcam Manager)
#
if [ -e /etc/init.d/softcam ] ; then
	/etc/init.d/softcam restart
fi
if [ -e /etc/init.d/cardserver ] ; then
	/etc/init.d/cardserver restart
fi

: exit 0
