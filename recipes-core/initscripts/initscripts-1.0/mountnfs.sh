#!/bin/sh
#
# mountnfs.sh Now that TCP/IP is configured, mount the network file
# systems in /etc/fstab if needed.
#

echo "Mounting remote filesystems..."

mount -a -t nfs,smbfs,cifs,ncpfs