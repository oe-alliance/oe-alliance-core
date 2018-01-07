FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

RDEPENDS_${PN}_append = " sdparm bash"

SRC_URI += "file://hotplug.sh \
            file://fastrestore_openatv.sh \
"

do_install_append() {
    # umountnfs should run before network stops (which is at K40)
    ln -sf        ../init.d/umountnfs.sh    ${D}${sysconfdir}/rc6.d/K31umountnfs.sh
    ln -sf        ../init.d/umountnfs.sh    ${D}${sysconfdir}/rc0.d/K31umountnfs.sh

    install -m 0755    ${WORKDIR}/hotplug.sh	${D}${sysconfdir}/init.d
    ln -sf        ../init.d/hotplug.sh      ${D}${sysconfdir}/rcS.d/S06hotplug.sh

    perl -i -pe 's:mount -a.+?$:mount -a -t nonfs,nfs4,smbfs,cifs,ncp,ncpfs,coda,ocfs2,gfs,gfs2,ceph -O no_netdev 2>/dev/null:' ${D}${sysconfdir}/init.d/mountall.sh
    perl -i -pe 's:(mount -a).*?$:$1:' ${D}${sysconfdir}/init.d/mountnfs.sh

    if [ "x${DISTRO}" = "xopenatv" ]; then
        install -m 0755    ${WORKDIR}/fastrestore_openatv.sh	${D}${sysconfdir}/init.d/fastrestore
        ln -sf        ../init.d/fastrestore      ${D}${sysconfdir}/rcS.d/S75fastrestore
    fi
    # run bootmisc.sh after S37populate-volatile.sh  to fix /tmp issue
    update-rc.d -f -r ${D} bootmisc.sh remove
    update-rc.d -r ${D} bootmisc.sh start 55 S .
}
