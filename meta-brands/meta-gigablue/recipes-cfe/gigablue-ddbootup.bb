SUMMARY = "ddbootup for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

PV = "1.0"
PR = "r1"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    echo '#! /bin/sh' > ${UNPACKDIR}/ddbootup
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd", "echo 'echo 1 > /proc/stb/lcd/mode' >> ${UNPACKDIR}/ddbootup" , "", d)}
    echo 'touch /dev/dbox/lcd0' >> ${UNPACKDIR}/ddbootup
    echo 'echo "${MACHINEBUILD}" > /proc/stb/info/gbmodel' >> ${UNPACKDIR}/ddbootup
    install -m 0755 ${UNPACKDIR}/ddbootup ${D}${sysconfdir}/init.d
    ${@bb.utils.contains("MACHINE", "gb7252", "ln -sf ../init.d/ddbootup ${D}${sysconfdir}/rcS.d/S66ddbootup" , "ln -sf ../init.d/ddbootup ${D}${sysconfdir}/rcS.d/S39ddbootup", d)}
}

FILES:${PN} += "${sysconfdir}"
