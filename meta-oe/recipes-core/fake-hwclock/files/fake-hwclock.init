#!/bin/sh

### BEGIN INIT INFO
# Provides:          hwclock
# Required-Start:
# Required-Stop:     umountroot
# Should-Stop:       
# X-Start-Before:    checkroot
# Default-Start:     S
# Default-Stop:      0 1 6
# Short-Description: Restore / save the current clock
# Description:       
### END INIT INFO

set -e

# Include core init functions if needed
#. /lib/lsb/init-functions

PARAM=/etc/default/fake-hwclock
if [ -f $PARAM ]; then
    . "$PARAM"
fi

case "${1:-}" in
  stop|reload|restart|force-reload)
        echo "Stopping fake hwclock: saving system time."
        fake-hwclock save;;

  start)
        echo "Starting fake hwclock: loading system time."
        fake-hwclock load $FORCE ;;

  *)
        echo "Usage: ${0:-} {start|stop|status|restart|reload|force-reload}" >&2
        exit 1
        ;;
esac

