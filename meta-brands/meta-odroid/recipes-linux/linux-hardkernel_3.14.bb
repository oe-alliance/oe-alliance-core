inherit kernel machine_kernel_pr
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

PV = "3.14.79+git${SRCPV}"
MACHINE_KERNEL_PR_append = "6"

KBRANCH ?= "odroidc2-3.14.y"
SRCREV ?= "6ad167426fbad87ff62af517fc01ad9655a89e18"

KBRANCH_odroidc2 = "odroidc2-3.14.y"
SRCREV_odroidc2 = "424ef5d5da4ca7a46b810ad91be13592efae3ebf"

#KERNEL_DEVICETREE_odroidc2 = "meson64_odroidc2.dtb"

SRC_URI = "git://github.com/hardkernel/linux.git;branch=${KBRANCH}"

SRC_URI += " \
	file://add_uboot.patch \
	file://defconfig"

KCONF_BSP_AUDIT_LEVEL = "0"

do_compile_append() {
	oe_runmake dtbs 
}

inherit deploy

do_deploy_append() {
	install -d ${DEPLOYDIR}
	install ${B}/arch/arm64/boot/dts/meson64_odroidc2.dtb ${DEPLOYDIR}/.
}

COMPATIBLE_MACHINE = "(odroidc2)"
