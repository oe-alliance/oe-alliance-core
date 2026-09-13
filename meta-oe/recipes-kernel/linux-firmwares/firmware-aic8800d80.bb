SUMMARY = "Firmware files for AIC8800"
require conf/license/license-close.inc

inherit allarch gitpkgv

SRC_URI = "git://github.com/oe-alliance-drivers/aic8800.git;protocol=https;branch=master;destsuffix=s"
SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

UNPACKDIR = "${WORKDIR}/u"
S = "${UNPACKDIR}/s/src/USB/driver_fw/fw/aic8800D80"
PR = "r1"

PACKAGES = "${PN}"

FILES:${PN} += "${nonarch_base_libdir}/firmware/aic8800D80"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/aic8800D80
    install -m 0644 ${S}/* ${D}${nonarch_base_libdir}/firmware/aic8800D80/
}
