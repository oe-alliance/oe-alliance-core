#! /bin/sh
### BEGIN INIT INFO
# Provides:          checkfs
# Required-Start:    checkroot
# Required-Stop:
# Should-Start:
# Default-Start:     S
# Default-Stop:
# X-Interactive:     true
# Short-Description: Check all filesystems.
### END INIT INFO

. /etc/default/rcS

#
# Check the rest of the filesystems.
#
if test ! -f /fastboot
then
    if test -f /forcefsck
    then
        force="-f"
    else
        force=""
    fi
    if test "$FSCKFIX"  = yes
    then
	fix="-y"
    else
	fix="-a"
    fi
    spinner="-C"
    case "$TERM" in
	dumb|network|unknown|"") spinner="" ;;
    esac
    test "`uname -m`" = "s390" && spinner="" # This should go away
    test "$VERBOSE" != no && echo "Checking all filesystems..."
    fsck $spinner -R -A $fix $force
    if test "$?" -gt 1
    then
      echo
      echo "fsck failed.  Please repair manually."
      echo
      echo "CONTROL-D will exit from this shell and continue system startup."
      echo
      # Start a single user shell on the console
      /sbin/sulogin $CONSOLE
    fi
fi
rm -f /fastboot /forcefsck

: exit 0
