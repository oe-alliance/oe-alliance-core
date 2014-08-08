SUMMARY = "Configuration files for online package repositories aka feeds"

require conf/license/license-gplv2.inc

RCONFLICTS_${PN} = "distro-feed-configs"
RREPLACES_${PN} = "distro-feed-configs"
PACKAGE_ARCH = "${MACHINEBUILD}"
PR = "r14"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg
    for feed in all ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH} 3rdparty ${MACHINE}_3rdparty ${MACHINEBUILD}; do
        echo "src/gz ${DISTRO_FEED_PREFIX}-${feed} ${DISTRO_FEED_URI}/${feed}" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
    done
    echo "src/gz ocram-picons http://picons.org/downloads/ipk" > ${S}/${sysconfdir}/opkg/ocram-feed.conf
}
do_install () {
        install -d ${D}${sysconfdir}/opkg
        install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}

CONFFILES_${PN} += '${@ " ".join( [ ( "${sysconfdir}/opkg/%s-feed.conf" % feed ) for feed in "all ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH} 3rdparty ${MACHINE}_3rdparty  ${MACHINEBUILD} ocram".split() ] ) }'
