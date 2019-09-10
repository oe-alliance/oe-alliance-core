PR .= ".7"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

RDEPENDS_${PN}_append = " sdparm bash"
RPROVIDES_${BPN} += "softcam-support cardserver-support"
RREPLACES_${BPN} += "softcam-support cardserver-support"
RCONFLICTS_${BPN} += "softcam-support cardserver-support"
RRECOMMENDS_${PN} = ""

SRC_URI += "file://hotplug.sh \
            file://nocam.sh \
            file://nocard.sh \
"

do_install_append() {
    # umountnfs should run before network stops (which is at K40)
    ln -sf        ../init.d/umountnfs.sh    ${D}${sysconfdir}/rc6.d/K31umountnfs.sh
    ln -sf        ../init.d/umountnfs.sh    ${D}${sysconfdir}/rc0.d/K31umountnfs.sh

    install -m 0755    ${WORKDIR}/hotplug.sh	${D}${sysconfdir}/init.d
    ln -sf        ../init.d/hotplug.sh      ${D}${sysconfdir}/rcS.d/S06hotplug.sh

    perl -i -pe 's:mount -a.+?$:mount -a -t nonfs,nfs4,smbfs,cifs,ncp,ncpfs,coda,ocfs2,gfs,gfs2,ceph -O no_netdev 2>/dev/null:' ${D}${sysconfdir}/init.d/mountall.sh
    perl -i -pe 's:(mount -a).*?$:$1:' ${D}${sysconfdir}/init.d/mountnfs.sh

    # run bootmisc.sh after S37populate-volatile.sh  to fix /tmp issue
    update-rc.d -f -r ${D} bootmisc.sh remove
    update-rc.d -r ${D} bootmisc.sh start 55 S .

    install -m 0755    ${WORKDIR}/nocard.sh	${D}${sysconfdir}/init.d/cardserver.None

    # Create the startup links for /etc/init.d/cardserver ...
    ln -sf cardserver.None ${D}/etc/init.d/cardserver
    update-rc.d -r ${D} cardserver start 95 S .

    # ... but avoid the link /etc/init.d/cardserver becoming a file of this package
    rm ${D}/etc/init.d/cardserver

    install -m 0755    ${WORKDIR}/nocam.sh	${D}${sysconfdir}/init.d/softcam.None

    # Create the startup links for /etc/init.d/softcam ...
    ln -sf softcam.None ${D}/etc/init.d/softcam
    update-rc.d -r ${D} softcam defaults 50

    # ... but avoid the link /etc/init.d/softcam becoming a file of this package
    rm ${D}/etc/init.d/softcam

}

do_install_append_u53() {
    ln -sf        ../init.d/networking      ${D}${sysconfdir}/rc3.d/S90networking

}

pkg_postinst_${PN}_append() {
	if [ ! -e "$D/etc/init.d/cardserver" ]
	then
		ln -s cardserver.None $D/etc/init.d/cardserver
	fi
	if [ ! -e "$D/etc/init.d/softcam" ]
	then
		ln -s softcam.None $D/etc/init.d/softcam
	fi
}
