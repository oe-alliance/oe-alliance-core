DESCRIPTION = "Samsung secure bootloader for Odroid XU3 devices supported by the hardkernel product"
SECTION = "bootloaders"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
DEPENDS += "u-boot u-boot-mkimage-native"

inherit deploy

S = "${WORKDIR}"

SRC_URI = "\
    file://autoboot.cmd \
    "
do_patch[noexec] = "1"
do_configure[noexec] = "1"

do_compile (){
        mkimage -C none -A arm -T script -d ${S}/autoboot.cmd ${S}/boot.scr
}

do_deploy() {
	install -d ${DEPLOYDIR}
	install  ${S}/boot.scr ${DEPLOYDIR}
}

addtask deploy before do_build after do_compile

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(odroid-xu3)"
