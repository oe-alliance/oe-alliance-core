DESCRIPTION = "Linux kernel from stlinux"
LICENSE = "GPLv2"
SECTION = "kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
KV = "2.6.32"
PR = "r8"

DEPENDS_spark7162 += " \
           stlinux24-sh4-stx7105-fdma-firmware \
"

DEPENDS_spark += " \
           stlinux24-sh4-stx7111-fdma-firmware \
"

inherit kernel machine_kernel_pr

SRCDATE = "20140717"
MACHINE_KERNEL_PR_append = ".26"

STM_PATCH_STR = "0216"
LINUX_VERSION = "2.6.32.61"
SRCREV = "cfb940db2f158eda3fba47d0e001612d13c10b40"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI = "git://git.stlinux.com/stm/linux-sh4-2.6.32.y.git;protocol=git;branch=stmicro \
    file://linux-sh4-linuxdvb_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-sound_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-time_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-init_mm_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-copro_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-strcpy_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-ext23_as_ext4_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-bpa2_procfs_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-ftdi_sio.c_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-lzma-fix_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-tune_stm24.patch;patch=1 \
    file://linux-sh4-console_missing_argument_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-mmap_stm24.patch;patch=1 \
    file://linux-sh4-cpuinfo.patch;patch=1 \
    file://linux-sh4-add_missing_eid.patch;patch=1 \
    file://kbuild-generate-mudules-builtin.patch;patch=1 \
    file://defconfig \
    file://st-coprocessor.h \
"

SRC_URI_append_spark7162 = " \
    file://linux-sh4-stmmac_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-lmb_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-spark7162_setup_stm24_${STM_PATCH_STR}.patch;patch=1 \
"

SRC_URI_append_spark = " \
    file://linux-sh4-stmmac_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-lmb_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-spark_setup_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-lirc_stm_stm24_${STM_PATCH_STR}.patch;patch=1 \
    file://linux-sh4-spark-af901x-NXP-TDA18218.patch;patch=1 \
    file://linux-sh4-spark-dvb-as102.patch;patch=1 \
"

S = "${WORKDIR}/git"
PARALLEL_MAKEINST = ""

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGETYPE = "uImage"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}"

KEEPUIMAGE = "true"

do_configure () {
    rm -f ${S}/.config || true
    cp ${WORKDIR}/defconfig ${S}/.config
    sed -i "s#^\(CONFIG_EXTRA_FIRMWARE_DIR=\).*#\1\"${STAGING_DIR_HOST}/lib/firmware\"#" .config;
        yes '' | oe_runmake oldconfig
    if test -e scripts/Makefile.fwinst ; then
        sed -i -e "s:-m0644:-m 0644:g" scripts/Makefile.fwinst
    fi
}

do_install_append() {
    kerneldir=${D}${KERNEL_SRC_PATH}
    if [ -f include/linux/bounds.h ]; then
        mkdir -p $kerneldir/include/linux
        cp include/linux/bounds.h $kerneldir/include/linux/bounds.h
    fi
    if [ -f include/asm-sh/machtypes.h ]; then
        mkdir -p $kerneldir/include/asm-sh
        cp include/asm-sh/machtypes.h $kerneldir/include/asm-sh
    fi
    install -d ${D}${includedir}/linux	
    install -m 644 ${WORKDIR}/st-coprocessor.h ${D}${includedir}/linux
    oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix}/src/linux-${KERNEL_VERSION} ARCH=$ARCH
    cp ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
}

# hack to override kernel.bbclass...
# uimages are already built in kernel compile
do_uboot_mkimage() {
	:
}

FILES_kernel-dev += "${includedir}/linux"

# bitbake.conf only prepends PARALLEL make in tasks called do_compile, which isn't the case for compile_modules
# So explicitly enable it for that in here
EXTRA_OEMAKE = "${PARALLEL_MAKE} "

PACKAGES =+ "kernel-headers"
FILES_kernel-headers = "${exec_prefix}/src/linux*"

pkg_postinst_kernel-image() {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
            flash_erase /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        fi
    fi
    true
}

