SUMMARY = "Mali-G31 Bifrost kernel driver for the GTMedia GTX Combo"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://../../../license.txt;md5=a8a201e4d223de5b4436b255157e7f6a"
COMPATIBLE_MACHINE = "^gtxcombo$"
inherit gitpkgv module

SRC_URI = "git://github.com/oe-alliance-kernels/gtx-mali.git;protocol=https;branch=5.15.196"
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r1"
S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}/kernel/drivers/gpu/arm"

# SM1 uses the Bifrost job manager. The board clock is managed by the
# Amlogic platform code in this driver.
EXTRA_OEMAKE += "CONFIG_MALI_DEVFREQ=n CONFIG_MALI_CSF_SUPPORT=n KCFLAGS=-DCONFIG_MALI_LOW_MEM=0"
KERNEL_MODULE_AUTOLOAD += "mali_kbase"

do_install:append() {
    rm -f ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/modules.order.*
}
