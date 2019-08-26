SUMMARY = "CCcam ${PV} softcam"

FILESEXTRAPATHS_prepend := "${THISDIR}/enigma2-plugin-softcams-cccam:"

PR = "r0"

DEPENDS = "libxcrypt"
RDEPENDS_${PN} = "libxcrypt"

SRC_URI = "http://downloads.pli-images.org/softcams/CCcam-${PV}.zip \
    file://CCcam.xml"

CAMNAME = "CCcam"

S = "${WORKDIR}"

require softcam.inc

INHIBIT_PACKAGE_STRIP = "1"

CONFFILES = "${sysconfdir}/CCcam.cfg ${sysconfdir}/ppanels/CCcam.xml"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/CCcam.${TARGET_ARCH} ${D}${bindir}/${CAMNAME}
    install -d ${D}${sysconfdir}
    install -m 0644 ${S}/CCcam.cfg ${D}${sysconfdir}/CCcam.cfg
    install -d ${D}${sysconfdir}/ppanels
    install -m 0644 ${WORKDIR}/CCcam.xml ${D}${sysconfdir}/ppanels/CCcam.xml
}

SRC_URI[md5sum] = "2f76eacbd286255a505dbc983df9cb6c"
SRC_URI[sha256sum] = "3c46de7a17357ebcde8ff31276ede67d0e05358b2eaf3b206d3e0242176f1de6"

INSANE_SKIP_${PN} = "already-stripped"
