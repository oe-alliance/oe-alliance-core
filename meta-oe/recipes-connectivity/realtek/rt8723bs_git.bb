SUMMARY = "Driver for Realtek 8723BS  wireless/bluetooth devices"
HOMEPAGE = "http://www.realtek.com/"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r4"


SRC_URI = "git://github.com/22ktv/rtl8723bs.git"

SRC_URI_append_sh4 = " \
    file://rt8723bs_sh4.patch;patch=1 \
    "

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR}"

do_configure_prepend () {
    export KDIR=${STAGING_KERNEL_DIR}
}

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake 'MODPATH={D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/staging/rtl8723bs' \
        'KERNEL_SOURCE=${STAGING_KERNEL_DIR}' \
        'LINUX_SRC=${STAGING_KERNEL_DIR}' \
        'KDIR=${STAGING_KERNEL_DIR}' \
        'KERNDIR=${STAGING_KERNEL_DIR}' \
        'KSRC=${STAGING_KERNEL_DIR}' \
        'KERNEL_VERSION=${KERNEL_VERSION}' \
        'KVER=${KERNEL_VERSION}' \
        'CC=${KERNEL_CC}' \
        'AR=${KERNEL_AR}' \
        'LD=${KERNEL_LD}'
}

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/staging/rtl8723bs
    install -m 0644 ${S}/r8723bs.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/staging/rtl8723bs
}

