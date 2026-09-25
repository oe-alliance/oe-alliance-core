DESCRIPTION = "Driver for the built-in Broadcom 43569 chip of the VU+ 4K boxes"
HOMEPAGE = "https://www.broadcom.com"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://dhd_linux.c;beginline=1;endline=29;md5=8a00d47c60ac37ac3bd4477848e8e433"
DEPENDS = "virtual/kernel"

inherit gitpkgv module-base

SRCREV = "${AUTOREV}"
PV = "1.363.110.17+git"
PKGV = "1.363.110.17+git${GITPKGV}"
PR = "r0"
SRC_URI = "git://github.com/oe-alliance-drivers/bcmdhd.git;protocol=https;branch=master;destsuffix=s"

S = "${UNPACKDIR}/s"

# The clean target of this Makefile points at the kernel tree of its origin.
CLEANBROKEN = "1"

COMPATIBLE_MACHINE = "^(vuduo4k|vuduo4kse|vuultimo4k)$"

# Building the wireless extensions as well leaves two layers driving the chip,
# and the one the kernel config brings in would win the association.
EXTRA_OEMAKE = " \
    ARCH=${ARCH} \
    CONFIG_CFG80211=y \
    CONFIG_CFG80211_WEXT= \
    CONFIG_WIRELESS_EXT= \
    CROSS_COMPILE=${TARGET_PREFIX} \
    DEPMOD=echo \
    INSTALL_MOD_PATH=${D} \
    M=${S} \
"

export KCFLAGS += " -std=gnu17 -Wno-error"

do_compile() {
	unset CC CFLAGS CPP CPPFLAGS CXX CXXFLAGS CCLD LDFLAGS
	oe_runmake -C ${STAGING_KERNEL_DIR} modules
}

do_install() {
	unset CC CFLAGS CPP CPPFLAGS CXX CXXFLAGS CCLD LDFLAGS
	oe_runmake -C ${STAGING_KERNEL_DIR} modules_install
}

FILES:${PN} = "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra"

pkg_postinst:${PN} () {
if [ -z "$D" ]; then
	depmod -a ${KERNEL_VERSION}
fi
}

addtask make_scripts after do_prepare_recipe_sysroot before do_compile
do_make_scripts[lockfiles] = "${TMPDIR}/kernel-scripts.lock"
do_make_scripts[depends] += "virtual/kernel:do_shared_workdir"

do_make_scripts() {
	:
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

# The kernel of this box has cfg80211 as a module of its own.
RDEPENDS:${PN}:append:vuduo4kse = " kernel-module-cfg80211"
