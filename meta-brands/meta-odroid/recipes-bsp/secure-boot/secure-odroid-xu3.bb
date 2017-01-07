DESCRIPTION = "Samsung secure bootloader for Odroid XU3 devices supported by the hardkernel product"
SECTION = "bootloaders"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = "\
    file://boot.ini \
    file://bl1.bin.hardkernel \
    file://bl2.bin.hardkernel \
    file://tzsw.bin.hardkernel \
    "

inherit deploy

S = "${WORKDIR}"

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_deploy () {
    install -d ${DEPLOY_DIR_IMAGE}
    install -m 755  ${S}/boot.ini ${DEPLOY_DIR_IMAGE}
    install -m 755  ${S}/bl1.bin.hardkernel ${DEPLOY_DIR_IMAGE}
    install -m 755  ${S}/bl2.bin.hardkernel ${DEPLOY_DIR_IMAGE}
    install -m 755  ${S}/tzsw.bin.hardkernel ${DEPLOY_DIR_IMAGE}
}

addtask deploy before do_build after do_compile

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE  = "odroid-xu3"
