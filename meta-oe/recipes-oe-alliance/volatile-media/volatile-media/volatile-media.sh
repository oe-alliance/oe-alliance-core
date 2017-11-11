#!/bin/sh

mountpoint -q "/media" || mount -t tmpfs -o size=64k tmpfs /media

create_point() {
	if [ ! -d $2 ]
	then
		mkdir -p $2
	fi
}

grep -v "^#" /etc/fstab | grep "\s/media/" | while read LINE
do
	create_point $LINE
done

if [ ! -e /dev/fuse ]
then
	modprobe fuse
fi
