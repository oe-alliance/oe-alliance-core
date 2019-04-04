#!/bin/sh

if ! test -e /.resizerootfs
then
    if grep -q mmcblk0p10 /proc/cmdline
    then
        echo "Resizing /dev/mmcblk0p10 Do not unplug power!..."
        resize2fs /dev/mmcblk0p10
        touch /.resizerootfs
    fi
fi

echo "dinobot start!!!"
cd /lib/modules/4.4.35/extra/
./load
