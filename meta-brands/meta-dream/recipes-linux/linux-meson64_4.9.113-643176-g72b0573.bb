DEPENDS = "libgcc"
PROVIDES = "linux-dreambox"
PE = "1"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR:append = ".2"

require linux-dreambox-4.9.inc

SRC_URI = "https://source.mynonpublic.com/dreambox/${BPN}-v${PV}.tar.xz \
           file://dmmaml_cec2.patch \
           file://ge2d.patch \
           file://hdmi_tx_spdif.patch \	 
           file://support-for-gcc12.patch \
           file://defonfig \
           file://fix-multiple-defs-yyloc_v1.patch \
           file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch \
           file://move-default-dialect-to-SMB3.patch \
           file://hide_sdcardfs_info.patch \
           file://use_address-of_operator_on_section_symbols.patch \
"

SRC_URI[md5sum] = "2b77df9bf64f825925172790c54f8ead"
SRC_URI[sha256sum] = "8d47072d819464d68b1b1072013795f925104dc09ad36e0bf9e0c7004087fed2"

KERNEL_CC += "${TOOLCHAIN_OPTIONS}"
KERNEL_LD += "${TOOLCHAIN_OPTIONS}"

S = "${WORKDIR}/${BPN}-v${PV}"
B = "${WORKDIR}/build"

CMDLINE = "${@kernel_console(d)} root=/dev/mmcblk0p7 rootwait rootfstype=ext4 no_console_suspend"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

DEFCONFIG = "meson64"

LINUX_VERSION = "4.9"
KERNEL_DEVICETREE = ""
KERNEL_DEVICETREE:dreamone = "dreamone.dtb"
KERNEL_DEVICETREE:dreamtwo = "dreamtwo.dtb"
KERNEL_IMAGETYPES = "Image.gz"

export KCFLAGS = "-Wno-error=misleading-indentation \
                  -Wno-error=parentheses \
                  -Wno-error=shift-overflow \
                  -Wno-error=array-bounds \
                  -Wno-error=array-compare \
                  -Wno-error=sizeof-array-div \
                  -Wno-error=bool-compare \
                  -Wno-error=maybe-uninitialized \
                  -Wno-error=unused-variable \
                  -Wno-error=stringop-overflow \
                  -Wno-error=stringop-overread \
                  -Wno-error=zero-length-bounds \
                  -Wno-error=builtin-declaration-mismatch \
                  -Wno-error=address \
                  -Wno-error=unused-const-variable \
"

KERNEL_FLASH_ARGS = "-c '${CMDLINE}'"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

do_install:prepend() {
	mv ${B}/arch/arm64/boot/dts/amlogic/${KERNEL_DEVICETREE} ${B}/arch/arm64/boot/
}

do_rm_work() {
}
