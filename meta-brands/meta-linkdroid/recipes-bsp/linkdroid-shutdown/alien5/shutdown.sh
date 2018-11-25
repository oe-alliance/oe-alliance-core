#!/bin/sh

rm /sbin/halt
rm /sbin/shutdown
rm /sbin/reboot
busybox ln -s /usr/bin/amlhalt /sbin/halt
busybox ln -s /usr/bin/amlhalt /sbin/shutdown
busybox ln -s /usr/bin/amlreboot /sbin/reboot

