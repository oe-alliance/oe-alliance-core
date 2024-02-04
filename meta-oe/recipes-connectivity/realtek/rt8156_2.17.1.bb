SUMMARY = "A kernel module for Realtek RTL8152/RTL8153/RTL8156 Based USB Ethernet Adapters"
HOMEPAGE = "https://www.realtek.com"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"

inherit module

LIC_FILES_CHKSUM = "file://ReadMe.txt;md5=bd3fdc588b21f7451c03b1bbaebeb4ce"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/atvcaptain/realtek-r8152-linux.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "KDIR=${STAGING_KERNEL_DIR}"

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" modules
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
    install -m 0644 ${S}/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}

