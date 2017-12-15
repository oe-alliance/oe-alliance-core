#!/bin/sh -e
### BEGIN INIT INFO
# Provides:          udev-hotplug
# Required-Start:    udev $local_fs
# Required-Stop:
# Should-Start:      udev-finish
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Re-Check attached devices
### END INIT INFO

[[ -x /sbin/mdev ]] && mdev -s
[[ -x /sbin/udevadm ]] && udevadm trigger

exit 0
