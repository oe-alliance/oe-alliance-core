SUMMARY = "swap create extent your momory"
MAINTAINER = "oe-a"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r2"

SRC_URI="file://createswap.sh"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rc3.d
    install -m 0755 ${WORKDIR}/createswap.sh ${D}${sysconfdir}/init.d/createswap.sh
    ln -sf ../init.d/createswap.sh ${D}${sysconfdir}/rc3.d/S98createswap
}
