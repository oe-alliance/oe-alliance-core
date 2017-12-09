#! /bin/sh
### BEGIN INIT INFO
# Provides:          hostname
# Required-Start:
# Required-Stop:
# Should-Start:      glibc
# Default-Start:     S
# Default-Stop:
# Short-Description: Set hostname based on /etc/hostname
# Description:       Read the machines hostname from /etc/hostname, and
#                    update the kernel value with this value.  If
#                    /etc/hostname is empty, the current kernel value
#                    for hostname is used.  If the kernel value is
#                    empty, the value 'localhost' is used.
### END INIT INFO

HOSTNAME=$(/bin/hostname)

hostname -b -F /etc/hostname 2> /dev/null
if [ $? -eq 0 ]; then
	exit
fi

# Busybox hostname doesn't support -b so we need implement it on our own
if [ -f /etc/hostname ];then
	hostname `cat /etc/hostname`
elif [ -z "$HOSTNAME" -o "$HOSTNAME" = "(none)" -o ! -z "`echo $HOSTNAME | sed -n '/^[0-9]*\.[0-9].*/p'`" ] ; then
	hostname localhost
fi
