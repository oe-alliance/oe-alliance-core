#! /bin/sh
### BEGIN INIT INFO
# Provides:          media
# Required-Start:    
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# X-Start-Before:    udev mountall
# Short-Description: Mounts and populates /media.
# Description:       Mounts a tmpfs over /media and
#                    creates mount points according
#                    to /etc/fstab entries.
### END INIT INFO

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
