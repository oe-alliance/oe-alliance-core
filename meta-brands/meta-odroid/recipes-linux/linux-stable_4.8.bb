inherit kernel
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

KBRANCH ?= "linux-4.8.y"
SRCREV ?= "1888926ea8d25287d9339ca618230867d63002f6"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;bareclone=1;branch=linux-4.8.y"

SRC_URI += "file://defconfig"

LINUX_VERSION = "4.8.3"
PV = "${LINUX_VERSION}+git${SRCPV}"

KCONF_BSP_AUDIT_LEVEL = "0"

COMPATIBLE_MACHINE_odroid-xu3  = "odroid-xu3"

DEPENDS = "lzop-native"
