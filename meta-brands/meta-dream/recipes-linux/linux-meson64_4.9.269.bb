DEPENDS = "libgcc"
PROVIDES = "linux-dreambox"
PE = "1"

inherit kernel machine_kernel_pr kernel-fixups gitpkgv

MACHINE_KERNEL_PR = "r0"

require linux-dreambox-4.9.inc

SRC_URI = "git://github.com/oe-alliance-kernels/linux-dreambox-meson64.git;protocol=https;branch=4.9.269 \
           file://defconfig \
"

SRCREV = "${AUTOREV}"
PV = "4.9.269+git"
PKGV = "4.9.269+git${GITPKGV}"

KERNEL_CC += "${TOOLCHAIN_OPTIONS}"
KERNEL_LD += "${TOOLCHAIN_OPTIONS}"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"
B = "${WORKDIR}/build"

CMDLINE = "${@kernel_console(d)} root=/dev/mmcblk0p7 rootwait rootfstype=ext4 no_console_suspend"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

DEFCONFIG = "meson64"

LINUX_VERSION = "4.9"
KERNEL_DEVICETREE = ""
KERNEL_DEVICETREE:dreamone = "dreamone.dtb"
KERNEL_DEVICETREE:dreamtwo = "dreamtwo.dtb"
KERNEL_IMAGETYPES = "Image.gz"

# KCFLAGS, not EXTRA_CFLAGS on the command line: that would override the
# EXTRA_CFLAGS the kernel Makefiles add to (fs/sdcardfs)
export KCFLAGS = "-Wno-error -Wno-attribute-alias"

KERNEL_FLASH_ARGS = "-c '${CMDLINE}'"

do_install:prepend() {
	mv ${B}/arch/arm64/boot/dts/amlogic/${KERNEL_DEVICETREE} ${B}/arch/arm64/boot/
}

do_rm_work() {
}
