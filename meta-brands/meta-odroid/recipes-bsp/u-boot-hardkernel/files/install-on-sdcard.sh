#!/bin/bash
#
# Copyright 2013, Reinhard Tartler <siretart@tauware.de>
#
# Permission is hereby granted, free of charge, to any person obtaining a copy 
# of this software and associated documentation files (the "Software"), to deal 
# in the Software without restriction, including without limitation the rights 
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
# copies of the Software, and to permit persons to whom the Software is 
# furnished to do so, subject to the following conditions:

# The above copyright notice and this permission notice shall be included in 
# all copies or substantial portions of the Software.

# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
# THE SOFTWARE.


set -e

device="${1:-/dev/sdc}"
bootdev="${device}1"
rootdev="${device}2"
bootscr="${2:-boot-hdmi-720p60hz.scr}"
variant="core-image-base"
machine="odroid-u2"

error () {
    echo "$*" >&2
}
error_fatal () {
    error "$*"; exit 1
}

if ! [ "`cat /sys/block/${device##*/}/removable`" = "1" ]; then
    error_fatal "Device $device is not removable"
fi

grep -q -e "^$bootdev" /proc/mounts && umount $bootdev
grep -q -e "^$rootdev" /proc/mounts && umount $rootdev

bootmountpoint=`mktemp -d`
rootmountpoint=`mktemp -d`

do_cleanup() {
    sync; sync
    umount $bootmountpoint || true
    umount $rootmountpoint || true
    rmdir $bootmountpoint
    rmdir $rootmountpoint
}

trap do_cleanup EXIT

signed_bl1_position=1
bl2_position=31
uboot_position=63
tzsw_position=2111

set -x

# delete bootsector and partition tables
dd if=/dev/zero of=$device bs=1024 count=4

# cf. http://lkcl.net/reports/odroid-u2.html
parted -s $device -- mklabel msdos
parted -s $device -- mkpart primary fat16 4096s 266239s
parted -s $device -- mkpart primary ext3 266240s 100%

# fusing the signed bootloader
dd iflag=dsync oflag=dsync if=./BL1.bin of=${device} seek=$signed_bl1_position
dd iflag=dsync oflag=dsync if=./BL2.bin of=${device} seek=$bl2_position
dd iflag=dsync oflag=dsync if=./u-boot.bin of=${device} seek=$uboot_position
dd iflag=dsync oflag=dsync if=./TZSW.bin of=${device} seek=$tzsw_position

mkfs -t vfat -n BOOT $bootdev
# the UUID is taken from the hardkernel boot.scr generator script
mkfs -t ext3 -L ROOT -U e139ce78-9841-40fe-8823-96a304a09859 $rootdev

mount $bootdev $bootmountpoint
mount $rootdev $rootmountpoint

cp -v $bootscr $bootmountpoint/boot.scr
cp -v zImage $bootmountpoint
touch $bootmountpoint/uInitrd
tar xf ${variant}-${machine}.tar.gz -C $rootmountpoint
