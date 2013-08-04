#!/bin/sh

mount -t tmpfs -o size=64k tmpfs /media

create_point() {
	if [ ! -d $2 ]
	then
		mkdir -p $2
		touch $2/.fstab
	fi
}

grep -v "^#" /etc/fstab | grep "\s/media/" | while read LINE
do
	create_point $LINE
done
