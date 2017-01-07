inherit kernel
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

KBRANCH ?= "odroidc2-3.14.y"
SRCREV ?= "6ad167426fbad87ff62af517fc01ad9655a89e18"

KBRANCH_odroidc2 ?= "odroidc2-3.14.y"
SRCREV_machine_odroidc2 ?= "6ad167426fbad87ff62af517fc01ad9655a89e18"



#KERNEL_DEVICETREE_odroidc2 = "meson64_odroidc2.dtb"

SRC_URI = "git://github.com/hardkernel/linux.git;branch=${KBRANCH}"

SRC_URI += " \
	file://add_uboot.patch \
	file://0001-compiler-gcc-integrate-the-various-compiler-gcc-345-.patch \
	file://defconfig"

PV = "3.14.29+git${SRCPV}"

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
