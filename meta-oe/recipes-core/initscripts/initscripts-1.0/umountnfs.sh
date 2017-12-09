#! /bin/sh
### BEGIN INIT INFO
# Provides:          umountnfs
# Required-Start:
# Required-Stop:     umountfs
# Should-Stop:       $network $portmap nfs-common
# Default-Start:
# Default-Stop:      0 6
# Short-Description: Unmount all network filesystems
### END INIT INFO

echo "Unmounting remote filesystems..."

umount -a -f -t nfs,smbfs,cifs,ncpfs
