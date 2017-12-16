#!/bin/sh -e
### BEGIN INIT INFO
# Provides:          brcm-shutdown
# Required-Start:    
# Required-Stop:     
# Should-Start:      
# Should-Stop:       halt
# X-Stop-After:      umountroot
# Default-Start:     
# Default-Stop:      0
# Short-Description: Shutdown the STB.
# Description:       Shutdown the STB.
### END INIT INFO

/usr/bin/turnoff_power

