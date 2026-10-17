#!/bin/sh
#
# The boot manager leaves its logo and boot menu on the OSD. Opening the
# framebuffer gives the OSD fresh, cleared memory; the boot logo of this
# slot is drawn into it before the pan shows it, so the boot menu turns
# into the logo, which fades in. Once the boot logo video shows its first
# frame, the logo fades out over it and the OSD is turned upright again.

[ "$1" = "stop" ] && exit 0

FB=/sys/class/graphics/fb0
LOGO=/boot/bootlogo.bmp
SHOWN=/sys/module/amvideo/parameters/first_frame_toggled

{
	if [ -f $LOGO ]; then
		echo 0 > $FB/osd_plane_alpha
		# 1280x720 RGB565 pixels behind the 138 byte header, rows bottom-up;
		# one write, the OSD write of the kernel fails on small unaligned ones
		dd if=$LOGO of=/dev/fb0 bs=1843200 skip=138 iflag=skip_bytes count=1 2>/dev/null
		echo 3 > $FB/osd_reverse
		fbset -fb /dev/fb0 -g 1280 720 1280 720 16
	fi
	echo 0,0 > $FB/pan
} 3</dev/fb0

[ -f $LOGO ] || exit 0

(
	# fade the logo in
	a=0
	while [ $a -lt 256 ] && [ "$(cat $FB/bits_per_pixel)" = "16" ]; do
		a=$((a + 16))
		echo $a > $FB/osd_plane_alpha
		sleep 0.03
	done
	n=80
	# the first boot may start a restore; be done before its progress shows
	[ -e /etc/enigma2/settings ] || n=20
	while [ $n -gt 0 ] && [ "$(cat $SHOWN 2>/dev/null)" != "1" ]; do
		sleep 0.1
		n=$((n - 1))
	done
	# fade the logo out over the boot logo video, stop once enigma2 takes over
	while [ $a -gt 0 ] && [ "$(cat $FB/bits_per_pixel)" = "16" ]; do
		a=$((a - 16))
		echo $a > $FB/osd_plane_alpha
		sleep 0.03
	done
	# leave the unscaled 32 bit framebuffer fbprogress of the first boot
	# restore draws into; it cannot switch from 16 bit itself
	if [ "$(cat $FB/bits_per_pixel)" = "16" ]; then
		fbset -fb /dev/fb0 -g 1280 720 1280 2160 32
		dd if=/dev/zero of=/dev/fb0 bs=3686400 count=1 2>/dev/null
		echo 0 > $FB/free_scale
		echo 0,0 > $FB/pan
	fi
	echo 256 > $FB/osd_plane_alpha
	echo 0 > $FB/osd_reverse
) &
