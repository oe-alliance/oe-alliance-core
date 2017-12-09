#! /bin/sh
### BEGIN INIT INFO
# Provides:          mountnfs
# Required-Start:    $local_fs
# Required-Stop:
# Should-Start:      $network $portmap nfs-common  udev-mtab
# Default-Start:     S
# Default-Stop:
# Short-Description: Wait for network file systems to be mounted
### END INIT INFO

echo "Mounting remote filesystems..."

mount -a -t nfs,smbfs,cifs,ncpfs
