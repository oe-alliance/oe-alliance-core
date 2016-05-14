#!/bin/sh
#DESCRIPTION=This script shows Network Connections
netstat | grep tcp
netstat | grep unix

echo ""
exit 0