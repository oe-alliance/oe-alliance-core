SUMMARY = "Virtual DVB inputs for the GTX Combo"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=801f80980d171dd6425610833a22dbe6"
COMPATIBLE_MACHINE = "^gtxcombo$"
inherit gitpkgv module
DEPENDS += "gtx-media-modules"
SRC_URI = "git://github.com/oe-alliance-kernels/gtx-vtuner.git;protocol=https;branch=5.15.196"
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r1"
S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"
EXTRA_OEMAKE += "GTX_MEDIA_SYMBOLS=${STAGING_INCDIR}/gtx-media-modules/Module.symvers"
KERNEL_MODULE_AUTOLOAD += "gtx_vtuner"

# External Kbuild creates an intermediate ordering list beside the module.
do_install:append() {
    rm -f ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/modules.order.*
}
