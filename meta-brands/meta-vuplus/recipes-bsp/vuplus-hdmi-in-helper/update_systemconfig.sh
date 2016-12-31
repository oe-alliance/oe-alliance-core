#!/bin/sh
### BEGIN INIT INFO
# Provides:          update_config
# Required-Start:
# Required-Stop:
# Default-Start:     3
# Default-Stop:
# Short-Description: update configuration
# Description:
### END INIT INFO

PATH=/sbin:/bin:/usr/sbin:/usr/bin

[ -x /usr/bin/update_systemconfig ] || exit 0

case "$1" in
	start)
		echo -n "Update system configuration..."
		/usr/bin/update_systemconfig
		;;
	*)
		;;
esac

exit 0
