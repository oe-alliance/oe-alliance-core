#!/bin/sh
parted -s /dev/mmcblk1 rm 4
parted -s /dev/mmcblk1 rm 5
parted -s /dev/mmcblk1 rm 6
parted -s /dev/mmcblk1 rm 7
parted -s /dev/mmcblk1 rm 8
parted -s /dev/mmcblk1 rm 9
parted /dev/mmcblk1 unit % resizepart 3 Y 100%
rm -f /.resizerootfs
