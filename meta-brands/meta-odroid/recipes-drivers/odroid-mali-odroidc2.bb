SUMMARY = "Hardkernel Mali Video driver for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;md5=d0d2f45bce10dd67cca4a749d12e535e"

PR = "r0"
KV = "3.14.79"

SRC_URI[md5sum] = "b849349480679ca6253cf2660e366ec6"

SRC_URI = "http://sources.libreelec.tv/devel/gpu-aml-r6p1-01rel0-2364187.tar.xz"

S = "${WORKDIR}/gpu-aml-r6p1-01rel0-2364187/mali"

inherit module

EXTRA_OEMAKE = "CONFIG_MALI400=m CONFIG_MALI450=m KDIR=${STAGING_KERNEL_BUILDDIR}"

do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C "${STAGING_KERNEL_BUILDDIR}" M="${S}" modules
}

do_install() {
	install -d ${D}${nonarch_base_libdir}/modules/${KV}/drivers/gpu/mali
	install -m 0644 ${S}/mali.ko ${D}${nonarch_base_libdir}/modules/${KV}/drivers/gpu/mali
}

