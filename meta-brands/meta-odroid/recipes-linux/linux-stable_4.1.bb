inherit kernel
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

KBRANCH ?= "linux-4.1.y"
SRCREV ?= "648d744eff1aedea4ffe49dfca07aa465669e1f4"

KBRANCH_odroid-xu3 ?= "linux-4.1.y"
SRCREV_machine_odroid-xu3 ?= "648d744eff1aedea4ffe49dfca07aa465669e1f4"

KERNEL_DEVICETREE_odroid-xu3 = "exynos5422-odroidxu3.dtb"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;bareclone=1;branch=${KBRANCH}"

SRC_URI += "file://defconfig"

LINUX_VERSION = "4.1.22"
PV = "${LINUX_VERSION}+git${SRCPV}"

KCONF_BSP_AUDIT_LEVEL = "0"

COMPATIBLE_MACHINE = "(odroid-xu3)"
