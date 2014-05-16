#!/bin/sh
#
# umountnfs.sh Unmount all network filesystems.
#

echo "Unmounting remote filesystems..."

umount -a -f -t nfs,smbfs,cifs,ncpfs
