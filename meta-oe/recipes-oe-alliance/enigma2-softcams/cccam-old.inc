SUMMARY = "Older CCcam ${PV} softcam"

DEPENDS = "libxcrypt"
RDEPENDS_${PN} = "libxcrypt"

PR = "r0"

SRC_URI = "http://downloads.pli-images.org/softcams/CCcam-${PV}.zip"

CAMNAME = "CCcam${CCAMV}"

S = "${WORKDIR}"

require softcam.inc

INHIBIT_PACKAGE_STRIP = "1"

CONFFILES = "${sysconfdir}/CCcam.cfg"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/CCcam.${TARGET_ARCH} ${D}${bindir}/${CAMNAME}
    install -d ${D}${sysconfdir}
    install -m 0644 ${S}/CCcam.cfg ${D}${sysconfdir}/CCcam.cfg
}

INSANE_SKIP_${PN} = "already-stripped"
