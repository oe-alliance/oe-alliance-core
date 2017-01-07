inherit kernel
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

KBRANCH ?= "linux-4.4.y"
SRCREV ?= "9ca1d50fa959cda1f04f43275f7930a70f1a631e"

KBRANCH_odroid-xu3 ?= "linux-4.4.y"
SRCREV_machine_odroid-xu3 ?= "9ca1d50fa959cda1f04f43275f7930a70f1a631e"

KERNEL_DEVICETREE_odroid-xu3 = "exynos5422-odroidxu3.dtb"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;bareclone=1;branch=${KBRANCH}"

SRC_URI += "file://defconfig"

LINUX_VERSION = "4.4.14"
PV = "${LINUX_VERSION}+git${SRCPV}"

KCONF_BSP_AUDIT_LEVEL = "0"

COMPATIBLE_MACHINE = "(odroid-xu3)"
