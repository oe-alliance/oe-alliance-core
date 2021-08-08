HOMEPAGE = "https://github.com/PLi-metas/stgfx2"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

DEPENDS = "directfb fulan-dvb-modules-${MACHINE}"

inherit module autotools pkgconfig

SRC_URI = "git://github.com/OpenVisionE2/stgfx2.git"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

do_configure:append() {
	cp -r ${S}/linux/kernel ${B}/linux/
}

# Kernel files are not compatble with 2.6.32
# do_compile:append() {
#	make -C "${STAGING_KERNEL_DIR}" M="${B}/linux/kernel" STM_BLITTER_TOPDIR="${B}/linux/kernel" modules
# }

# MACHINE_KERNEL_PR:append = ".1"
# EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

FILES:${PN} += "\
  ${libdir}/directfb-1.7-7/gfxdrivers/*.so \
"

FILES:${PN}-dev += "\
  ${libdir}/directfb-1.7-7/gfxdrivers/*.la \
"
