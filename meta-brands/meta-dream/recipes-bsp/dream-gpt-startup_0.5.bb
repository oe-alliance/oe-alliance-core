DESCRIPTION = "MultibootSelection startup files for dreambox"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://startup.zip \
    file://startup.sh \
    file://dream-data.sh \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 -d ${D}${sysconfdir}/rc3.d
    install -m 0755 ${UNPACKDIR}/startup.sh ${D}${sysconfdir}/init.d/startup.sh
    install -m 0755 ${UNPACKDIR}/dream-data.sh ${D}${sysconfdir}/init.d/dream-data.sh
    ln -sf   ../init.d/startup.sh ${D}${sysconfdir}/rc3.d/S85startup.sh
    install -m 0755 -d ${D}/usr/share/startup
    install -m 0755 ${UNPACKDIR}/STARTUP ${D}/usr/share/startup/STARTUP
    install -m 0755 ${UNPACKDIR}/STARTUP_1 ${D}/usr/share/startup/STARTUP_1
    install -m 0755 ${UNPACKDIR}/STARTUP_2 ${D}/usr/share/startup/STARTUP_2
    install -m 0755 ${UNPACKDIR}/STARTUP_3 ${D}/usr/share/startup/STARTUP_3
    install -m 0755 ${UNPACKDIR}/STARTUP_4 ${D}/usr/share/startup/STARTUP_4
    install -m 0755 ${UNPACKDIR}/STARTUP_RECOVERY ${D}/usr/share/startup/STARTUP_RECOVERY
}

FILES:${PN} = "/usr/share ${sysconfdir}"
