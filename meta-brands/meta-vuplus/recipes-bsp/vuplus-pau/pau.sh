#!/bin/sh
### BEGIN INIT INFO
# Provides:          pau
# Required-Start:
# Required-Stop:
# Default-Start:     2 3 4 5
# Default-Stop:
# Short-Description: pau
# Description:
### END INIT INFO

PATH=/sbin:/bin:/usr/sbin:/usr/bin

[ -x /usr/bin/pau ] || exit 0

case "$1" in
start)
        echo -n "Running pau..."
        pau
        echo "done."
        ;;
*)
        ;;
esac

exit 0

