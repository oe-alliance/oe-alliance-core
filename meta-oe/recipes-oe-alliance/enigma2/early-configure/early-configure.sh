#!/bin/sh
#
### BEGIN INIT INFO
# Provides:          early-configure
# Required-Start:    $local_fs
# Required-Stop:
# Should-Start:
# Default-Start:     S
# Default-Stop:
# Short-Description: Wait for network file systems to be mounted
# Description:       Network file systems are mounted by
#                    /etc/network/if-up.d/mountnfs in the background
#                    when interfaces are brought up; this script waits
#                    for them to be mounted before carrying on.
### END INIT INFO
# Script to fix some configure package getting run too late
opkg configure update-modules

# suicide
rm -f /etc/rcS.d/S*early-configure.sh
