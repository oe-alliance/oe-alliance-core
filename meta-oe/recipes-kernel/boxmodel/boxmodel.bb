SUMMARY = "BoxModel proc Helper"
MAINTAINER = "Dr. Ideal"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
DEPENDS = "virtual/kernel"

PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

SRC_URI = "file://boxmodel.c file://Makefile"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"
PV = "1.0"

inherit module

# need only for dreambox linux-meson64 4.9
export KCFLAGS += " -Wno-error=unused-variable"

do_compile () {  
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake 'MODPATH="${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/boxmodel" ' \
    -C "${STAGING_KERNEL_BUILDDIR}" SUBDIRS="${S}" modules
}

do_install () {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/boxmodel
    install -m 0644 ${S}/boxmodel.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/boxmodel
    install -d ${D}/${sysconfdir}/modules-load.d
    echo boxmodel >> ${D}/${sysconfdir}/modules-load.d/zzboxmodel.conf
}

FILES:${PN} += "${sysconfdir}/modules-load.d/zzboxmodel.conf"
