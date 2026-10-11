#!/bin/sh
#
# Load the DVB driver ahead of modutils, which loads the modules one after
# the other and the DVB driver late in the list. enigma2 starts sooner.

[ "$1" = "stop" ] && exit 0

modprobe meson64 >/dev/null 2>&1 &
