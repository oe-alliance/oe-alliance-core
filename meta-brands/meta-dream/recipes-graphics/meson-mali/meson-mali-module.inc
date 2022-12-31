SUMMARY = "Mali kernel module"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${GPU_MODEL}/${GPU_VERSION}/kernel/license.txt;md5=13e14ae1bd7ad5bff731bba4a31bb510"

inherit module-base

SRC_URI:append = " file://0001-${GPU_MODEL}-${GPU_VERSION}-compile-fix-for-dreambox-kernel.patch"

GPU_MODEL = "${@d.getVar('BPN').split('-')[-2]}"
GPU_VERSION = "${@d.getVar('BPN').split('-')[-1]}"

EXTRA_OEMAKE += " \
    ARCH=${ARCH} \
    CONFIG_MALI_MIDGARD=m \
    CONFIG_MALI_MIDGARD_DVFS=y \
    CONFIG_MALI_PLATFORM_DEVICETREE=y \
    CROSS_COMPILE=${TARGET_PREFIX} \
    DEPMOD=echo \
    INSTALL_MOD_PATH=${D} \
    M=${S}/${GPU_MODEL}/${GPU_VERSION}/kernel/drivers/gpu/arm/midgard \
"

EXTRA_CFLAGS = "-DCONFIG_MALI_PLATFORM_DEVICETREE -DCONFIG_MALI_MIDGARD_DVFS -DCONFIG_MALI_BACKEND=gpu"

do_compile() {
    unset CC CFLAGS CPP CPPFLAGS CXX CXXFLAGS CCLD LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_DIR} EXTRA_CFLAGS="${EXTRA_CFLAGS}"
}

do_install() {
    unset CC CFLAGS CPP CPPFLAGS CXX CXXFLAGS CCLD LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_DIR} modules_install

    install -d ${D}${sysconfdir}/modules-load.d
    echo mali_kbase > ${D}${sysconfdir}/modules-load.d/00-${BPN}.conf
    chmod 0644 ${D}${sysconfdir}/modules-load.d/00-${BPN}.conf
}

FILES:${PN} = "${sysconfdir}/modules-load.d \
               /lib/modules/${KERNEL_VERSION}/extra"

pkg_postinst:${PN} () {
if [ -z "$D" ]; then
        depmod -a ${KERNEL_VERSION}
fi
}

COMPATIBLE_MACHINE = "^(meson64)$"

addtask make_scripts after do_prepare_recipe_sysroot before do_compile
do_make_scripts[lockfiles] = "${TMPDIR}/kernel-scripts.lock"
do_make_scripts[depends] += "virtual/kernel:do_shared_workdir"
