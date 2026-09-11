#!/bin/sh

# Only explicit automatic network mounts need a network worker. In particular,
# a box with no network mounts must never wait for DHCP at boot.
awk '
    /^[[:space:]]*#/ || NF < 4 { next }
    $4 ~ /(^|,)noauto(,|$)/ { next }
    $3 ~ /^(nfs|nfs4|smbfs|cifs|ncp|ncpfs|coda|ocfs2|gfs|gfs2|ceph)$/ ||
    $4 ~ /(^|,)_netdev(,|$)/ { found = 1; exit }
    END { exit !found }
' /etc/fstab || exit 0

# rc sources *.sh synchronously. Network readiness and remote mounts belong
# in a detached process, never in that boot path.
case "$1" in
    --worker)
        exec flock --close --nonblock /run/oe-mountnfs.lock "$0" --mount
        ;;
    --mount) ;;
    *)
        start-stop-daemon --start --background --exec /etc/init.d/mountnfs.sh -- --worker
        exit $?
        ;;
esac

# The worker is independent of boot; keep the existing bounded readiness wait.
# IPv6-only networks are valid too, and LAN mounts do not need a default route.
network_ready() {
    ip addr show scope global 2>/dev/null | awk '
        /inet / || (/inet6 / && !/tentative|dadfailed/) { found = 1 }
        END { exit !found }
    '
}

i=0
while [ "$i" -lt 30 ]; do
    if network_ready; then
        mount_result=0
        mount -a -t nfs,nfs4,smbfs,cifs,ncp,ncpfs,coda,ocfs2,gfs,gfs2,ceph || mount_result=$?
        # Also support fstab's generic _netdev flag on other filesystem types.
        mount -a -O _netdev -t nonfs,nfs4,smbfs,cifs,ncp,ncpfs,coda,ocfs2,gfs,gfs2,ceph || mount_result=$?
        exit "$mount_result"
    fi
    i=$((i+1))
    sleep 1
done

logger -t mountnfs "Network not ready after 30s; skipping automatic network mounts"
exit 0
