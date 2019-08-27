SUMMARY = "CCcam ${PV} softcam"

PR = "r0"

DEPENDS = "libxcrypt"
RDEPENDS_${PN} = "libxcrypt"

SRC_URI = "http://downloads.openpli.org/softcams/CCcam-${PV}.zip;name=softcam \
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

SRC_URI[softcam.md5sum] = "042bc8d82075dbc806787270ffa237f6"
SRC_URI[softcam.sha256sum] = "2b589e8181b927c0c0a7b0a045c500b1270234f8fab0848ba05201e339df2b51"

INSANE_SKIP_${PN} = "already-stripped"
