SUMMARY = "GTX CIMaX USB transport router and receiver startup"
LICENSE = "GPL-2.0-or-later AND LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=8832f58d29f923b0b40e59d5ad171a77"
COMPATIBLE_MACHINE = "^gtxcombo$"

SRC_URI = "git://github.com/oe-alliance-kernels/gtx-cimax-usb.git;protocol=https;branch=5.15.196"
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r1"
S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit gitpkgv module update-rc.d
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/modules.order.* ${sysconfdir}/init.d/gtx-ts-router"
RDEPENDS:${PN} += "gtx-cimax-firmware kernel-module-gtx-cimax-usb"
INITSCRIPT_NAME = "gtx-ts-router"
INITSCRIPT_PARAMS = "start 45 S ."

MAKE_TARGETS = "modules"
EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}/SRC"
MODULES_MODULE_SYMVERS_LOCATION = "SRC"

do_install:append() {
    install -Dm0755 ${S}/gtx-ts-router ${D}${sysconfdir}/init.d/gtx-ts-router
}
