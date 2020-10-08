#!/bin/sh
#DESCRIPTION=This script will delete all crash-logs and debug-logs from /home/root, /media/hdd, /media/usb
rm -rf /media/hdd/logs/Enigma2_crash* /media/usb/logs/Enigma2_crash* /home/root/logs/Enigma2_crash*
rm -rf /media/hdd/logs/Enigma2_debug* /media/usb/logs/Enigma2_debug* /home/root/logs/Enigma2_debug*
echo ""
exit 0