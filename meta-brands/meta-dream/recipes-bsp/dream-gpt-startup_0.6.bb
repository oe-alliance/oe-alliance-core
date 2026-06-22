DESCRIPTION = "MultibootSelection startup files for dreambox"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://startup.sh \
    file://dream-data.sh \
"

S = "${UNPACKDIR}"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 -d ${D}${sysconfdir}/rc3.d
    install -m 0755 ${UNPACKDIR}/startup.sh ${D}${sysconfdir}/init.d/startup.sh
    install -m 0755 ${UNPACKDIR}/dream-data.sh ${D}${sysconfdir}/init.d/dream-data.sh
    ln -sf   ../init.d/startup.sh ${D}${sysconfdir}/rc3.d/S85startup.sh
}

FILES:${PN} = "${sysconfdir}"
