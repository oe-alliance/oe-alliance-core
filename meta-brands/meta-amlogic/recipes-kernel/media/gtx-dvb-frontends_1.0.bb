SUMMARY = "GTX Combo DVB frontend drivers and Amlogic attachment callbacks"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://aml_dvb_extern_wrappers.c;beginline=1;endline=18;md5=b2e4d2e73c369838850f2319bfccb22f"
COMPATIBLE_MACHINE = "^gtxcombo$"

SRC_URI = "git://github.com/oe-alliance-kernels/gtx-dvb-frontends.git;protocol=https;branch=5.15.196"
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r1"
S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit gitpkgv module
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/modules.order.*"

MAKE_TARGETS = "modules"
EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}"
KERNEL_MODULE_AUTOLOAD += "gtx_frontends gtx_antenna_power"
RDEPENDS:${PN} += "gtx-sat-firmware kernel-module-gtx-antenna-power"
do_compile:append() {
    test -s "${S}/gtx_frontends.ko" || bbfatal "Kbuild did not produce gtx_frontends.ko"
}
