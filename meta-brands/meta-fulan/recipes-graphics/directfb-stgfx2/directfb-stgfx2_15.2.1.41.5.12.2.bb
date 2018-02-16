HOMEPAGE = "https://github.com/PLi-metas/stgfx2"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

DEPENDS = "directfb fulan-dvb-modules-${MACHINE}"

inherit module autotools pkgconfig

SRC_URI = "git://github.com/PLi-metas/stgfx2.git"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

do_configure_append() {
	cp -r ${S}/linux/kernel ${B}/linux/
}

# Kernel files are not compatble with 2.6.32
# do_compile_append() {
#	make -C "${STAGING_KERNEL_DIR}" M="${B}/linux/kernel" STM_BLITTER_TOPDIR="${B}/linux/kernel" modules
# }

# MACHINE_KERNEL_PR_append = ".1"
# EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

FILES_${PN} += "\
  ${libdir}/directfb-1.7-7/gfxdrivers/*.so \
"

FILES_${PN}-dev += "\
  ${libdir}/directfb-1.7-7/gfxdrivers/*.la \
"
