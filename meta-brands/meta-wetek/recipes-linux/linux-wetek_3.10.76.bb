DESCRIPTION = "Amlogic Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI[md5sum] = "ad154243840671482ff94caea3f80b47"
SRC_URI[sha256sum] = "1925da3f7fc23fb34e7418c2b57eeec004646b7e63c5fc2c800a1fc1e7b8f499"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".1"
DEPENDS = "xz-native bc-native u-boot-mkimage-native gcc"

# Avoid issues with Amlogic kernel binary components
INSANE_SKIP_${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_STRIP = "1"
LINUX_VERSION ?= "3.10.76"
LINUX_VERSION_EXTENSION ?= "amlogic"

COMPATIBLE_MACHINE = "(wetekplay)"

SRC_URI = "http://github.com/wetek-enigma/linux-wetek/archive/master.tar.gz \
    file://defconfig \
    file://fix-blocking-demux.patch \
"

S = "${WORKDIR}/linux-wetek-master"
B = "${WORKDIR}/build"


do_configure_prepend () {
    cd ${STAGING_KERNEL_DIR}
    find -type f -name "*.z" -print0 | xargs -0 cp -f --parents -t ${B}
}

do_compile_prepend () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
    if test -n "${KERNEL_DEVICETREE}"; then
        for DTB in ${KERNEL_DEVICETREE}; do
            if echo ${DTB} | grep -q '/dts/'; then
                bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
                DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
            fi
            oe_runmake ${DTB} CC="${KERNEL_CC}" LD="${KERNEL_LD}" ${KERNEL_EXTRA_ARGS}
        done
    # Create directory, this is needed for out of tree builds
    mkdir -p ${B}/arch/arm/boot/dts/amlogic/
    fi
}

# Put debugging files into dbg package
FILES_kernel-dbg += "/usr/src/kernel/drivers/amlogic/input/touchscreen/gslx680/.debug"


do_install_append () {
    ln -s ${STAGING_KERNEL_DIR}/arch/arm/mach-meson6 ${STAGING_KERNEL_DIR}/include/mach
    touch ${STAGING_KERNEL_DIR}/include/linux/smp_lock.h
    # remove *.z from installation path those are object files from amlogic for binary modules
    find ${D}/usr/src/kernel -type f -name "*.z" | xargs rm -f
}

do_rm_work() {
}