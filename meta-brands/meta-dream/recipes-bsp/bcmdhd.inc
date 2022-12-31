SUMMARY = "Broadcom DHD driver"
LICENSE = "GPL-2.0-or-later"
DEPENDS = "virtual/kernel"

inherit module-base

M ?= "${S}"

EXTRA_OEMAKE += " \
    ARCH=${ARCH} \
    CONFIG_BCMDHD_SDIO=y \
    CROSS_COMPILE=${TARGET_PREFIX} \
    DEPMOD=echo \
    INSTALL_MOD_PATH=${D} \
    KDIR=${STAGING_KERNEL_DIR} \
    M=${M} \
"

do_compile() {
    unset CC CFLAGS CPP CPPFLAGS CXX CXXFLAGS CCLD LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_DIR} modules
}
do_install() {
    unset CC CFLAGS CPP CPPFLAGS CXX CXXFLAGS CCLD LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_DIR} modules_install

    install -d ${D}${sysconfdir}/modules-load.d
    echo dhd > ${D}${sysconfdir}/modules-load.d/00-${BPN}.conf
    chmod 0644 ${D}${sysconfdir}/modules-load.d/00-${BPN}.conf
}

FILES:${PN} = "${sysconfdir}/modules-load.d \
               /lib/modules/${KERNEL_VERSION}/extra"

pkg_postinst:${PN} () {
if [ -z "$D" ]; then
	depmod -a ${KERNEL_VERSION}
fi
}

RDEPENDS:${PN} = "bcmdhd-firmware"

addtask make_scripts after do_prepare_recipe_sysroot before do_compile
do_make_scripts[lockfiles] = "${TMPDIR}/kernel-scripts.lock"
do_make_scripts[depends] += "virtual/kernel:do_shared_workdir"
