#!/bin/sh
#
### BEGIN INIT INFO
# Provides:          early-configure
# Required-Start:    checkroot-volatile
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Script to fix some configures getting run too late
# Description:       Script to fix some configures getting run too late
### END INIT INFO

opkg configure update-modules

# suicide
rm -f /etc/rcS.d/S*early-configure.sh /etc/init.d/S*early-configure.sh
