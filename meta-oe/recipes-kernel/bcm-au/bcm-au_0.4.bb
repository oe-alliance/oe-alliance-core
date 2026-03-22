SUMMARY = "Kernel Modul BCM_AU"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
DEPENDS = "virtual/kernel"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit module

PR = "r1"
PV = "0.4"

SRC_URI = "file://Makefile \
           file://aes256.c \
           file://aes256.h \
           file://bcm_au.c \
           file://bcm_au.rules \
           file://COPYING \
          "

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

python do_package:prepend() {
    d.prependVar('PKGV', '+')
    d.prependVar('PKGV', d.getVar("KERNEL_VERSION", True).split("-")[0])
}

export KCFLAGS += "${@bb.utils.contains("TARGET_ARCH", "mipsel", "-std=gnu17", "", d)}"

do_compile () {  
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake 'MODPATH="${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bcm" ' \
    -C "${STAGING_KERNEL_BUILDDIR}" SUBDIRS="${S}" modules
}

do_install() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bcm
    install -m 0644 ${UNPACKDIR}/bcm.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bcm
    install -d ${D}/${sysconfdir}/modules-load.d
    echo bcm >> ${D}/${sysconfdir}/modules-load.d/bcm.conf
}

FILES:${PN} += "${sysconfdir}/modules-load.d/bcm.conf"
FILES:${PN}-dbg = ""
