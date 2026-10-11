#!/bin/sh
#
# The boot manager leaves its logo and boot menu on the OSD. Opening the
# framebuffer gives the OSD fresh, cleared memory and the pan shows it.

[ "$1" = "stop" ] && exit 0

{ echo 0,0 > /sys/class/graphics/fb0/pan; } 3</dev/fb0
