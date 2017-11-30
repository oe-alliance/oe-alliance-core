SUMMARY = "Configuration files for online package repositories aka feeds (${PKGTYPE} version)"

require conf/license/license-gplv2.inc

RCONFLICTS_${PN} = "distro-feed-configs"
RREPLACES_${PN} = "distro-feed-configs"
PACKAGE_ARCH = "${MACHINEBUILD}"
PR = "r0"



PKGTYPE = "deb"
PKGCONFDIR = "${sysconfdir}/apt/sources.list.d"
PKGCONFSUFFIX = "list"

do_compile() {
    for feed in all ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH} 3rdparty ${MACHINE}_3rdparty ; do
        echo "deb ${DISTRO_FEED_URI}/${PKGTYPE}/$feed ./" > ${S}${PKGCONFDIR}/$feed-feed.${PKGCONFSUFFIX}
    done
}


FEED_ARCHS = "all ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH}"

do_compile_prepend() {
    install -d ${S}${PKGCONFDIR}
}
do_install() {
    install -d ${D}${PKGCONFDIR}
    install -m 0644 ${S}${PKGCONFDIR}/*-feed.${PKGCONFSUFFIX} ${D}${PKGCONFDIR}
}


CONFFILES_${PN} += '${@" ".join([("${PKGCONFDIR}/%s-feed.${PKGCONFSUFFIX}" % feed) for feed in "all ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH} 3rdparty ${MACHINE}_3rdparty".split()])}'
