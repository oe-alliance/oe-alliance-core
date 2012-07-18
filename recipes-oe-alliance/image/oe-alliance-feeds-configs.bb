DESCRIPTION = "Configuration files for online package repositories aka feeds"

require conf/license/license-gplv2.inc

RCONFLICTS_${PN} = "distro-feed-configs"
RREPLACES_${PN} = "distro-feed-configs"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "r1"

do_compile() {
	mkdir -p ${S}/${sysconfdir}/opkg
	for feed in all ${TARGET_ARCH} ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH} 3rdparty; do
		echo "src/gz ${DISTRO_FEED_PREFIX}-${feed} ${DISTRO_FEED_URI}/${feed}" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
	done
}
do_install () {
		install -d ${D}${sysconfdir}/opkg
		install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
		if [ -e ${D}${sysconfdir}/opkg/mipsel-feed.conf] ; then
			rm -f ${D}${sysconfdir}/opkg/mipsel-feed.conf
		fi
}

CONFFILES_${PN} += '${@ " ".join( [ ( "${sysconfdir}/opkg/%s-feed.conf" % feed ) for feed in "all ${TARGET_ARCH} ${PACKAGE_EXTRA_ARCHS} ${MACHINE_ARCH} 3rdparty".split() ] ) }'
