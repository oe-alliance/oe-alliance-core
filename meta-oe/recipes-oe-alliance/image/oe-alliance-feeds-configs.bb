SUMMARY = "Configuration files for online package repositories aka feeds"

require conf/license/license-gplv2.inc

RCONFLICTS:${PN} = "distro-feed-configs"
RREPLACES:${PN} = "distro-feed-configs"
PACKAGE_ARCH = "${MACHINEBUILD}"
PR = "r3"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg
    for feed in all ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH} 3rdparty ${MACHINE}_3rdparty ; do
        if [ "${feed}" = "static-${MACHINE}" ] || [ "${feed}" = "static-${TUNE_PKGARCH}" ]; then
            echo "src/gz ${DISTRO_FEED_PREFIX}-${feed} ${STATIC_DISTRO_FEED_URI}/${feed}" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
        else
            echo "src/gz ${DISTRO_FEED_PREFIX}-${feed} ${DISTRO_FEED_URI}/${feed}" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
        fi
    done
}
do_install () {
        install -d ${D}${sysconfdir}/opkg
        install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}

CONFFILES:${PN} += '${@ " ".join( [ ( "${sysconfdir}/opkg/%s-feed.conf" % feed ) for feed in "all ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH} 3rdparty ${MACHINE}_3rdparty  ocram".split() ] ) }'
