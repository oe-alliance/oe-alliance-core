DESCRIPTION = "Tools for ethernet bridging."
HOMEPAGE = "http://bridge.sourceforge.net/"
SECTION = "console/network"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=f9d20a453221a1b7e32ae84694da2c37"

SRC_URI = "${SOURCEFORGE_MIRROR}/bridge/bridge-utils-${PV}.tar.gz"

inherit autotools

EXTRA_OECONF = "--with-linux-headers=${STAGING_INCDIR}"

do_install_append () {
	mv ${D}${sbindir}/brctl ${D}${sbindir}/brctl.${PN}
	install -d ${D}/${datadir}/bridge-utils
	install -d ${D}/${sysconfdir}/network/if-pre-up.d
	install -d ${D}/${sysconfdir}/network/if-post-down.d
}

DEPENDS = "sysfsutils"
RRECOMMENDS_${PN} = "kernel-module-bridge"

pkg_postinst_${PN} () {
	update-alternatives --install ${sbindir}/brctl brctl brctl.${PN} 100
}

pkg_prerm_${PN} () {
	update-alternatives --remove brctl brctl.${PN}
}

SRC_URI[md5sum] = "0182fcac3a2b307113bbec34e5f1c673"
SRC_URI[sha256sum] = "876975e9bcc302aa8b829161ea3348b12b9b879f1db0dc98feaed8d0e5dd5933"
