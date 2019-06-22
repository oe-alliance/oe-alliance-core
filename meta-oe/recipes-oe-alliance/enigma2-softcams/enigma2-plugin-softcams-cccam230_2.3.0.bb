SUMMARY = "CCcam ${PV} softcam"

FILESEXTRAPATHS_prepend := "${THISDIR}/enigma2-plugin-softcams-cccam:"

PR = "r0"

DEPENDS = "virtual/crypt"
RDEPENDS_${PN} = "libxcrypt-compat"

SRC_URI = "http://downloads.pli-images.org/softcams/CCcam-${PV}.zip \
    file://CCcam.xml"

CAMNAME = "CCcam"

S = "${WORKDIR}"

require softcam.inc

INHIBIT_PACKAGE_STRIP = "1"

CONFFILES = "/etc/CCcam.cfg /etc/ppanels/CCcam.xml"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/CCcam.${TARGET_ARCH} ${D}/usr/bin/${CAMNAME}
    install -d ${D}/etc
    install -m 0644 ${S}/CCcam.cfg ${D}/etc/CCcam.cfg
    install -d ${D}/etc/ppanels
    install -m 0644 ${WORKDIR}/CCcam.xml ${D}/etc/ppanels/CCcam.xml
}

SRC_URI[md5sum] = "2f76eacbd286255a505dbc983df9cb6c"
SRC_URI[sha256sum] = "3c46de7a17357ebcde8ff31276ede67d0e05358b2eaf3b206d3e0242176f1de6"

INSANE_SKIP_${PN} = "already-stripped"
