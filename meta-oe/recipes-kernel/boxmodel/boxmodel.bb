SUMMARY = "BoxModel proc Helper"
MAINTAINER = "Dr. Ideal"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
DEPENDS = "virtual/kernel"

PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

SRC_URI = "file://boxmodel.c file://Makefile"

S = "${WORKDIR}"
PV = "1.0"
PR = "r10"

inherit module machine_kernel_pr

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake 'MODPATH="${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/boxmodel" ' \
        'KERNEL_SOURCE="${STAGING_KERNEL_BUILDDIR}" ' \
        'KDIR="${STAGING_KERNEL_BUILDDIR}"' \
        'KERNEL_VERSION="${KERNEL_VERSION}" ' \
        'CC="${KERNEL_CC}" ' \
        'LD="${KERNEL_LD}" '

}

do_install () {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/boxmodel
    install -m 0644 ${S}/boxmodel.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/boxmodel
    install -d ${D}/${sysconfdir}/modules-load.d
    echo boxmodel >> ${D}/${sysconfdir}/modules-load.d/zzboxmodel.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/zzboxmodel.conf"
