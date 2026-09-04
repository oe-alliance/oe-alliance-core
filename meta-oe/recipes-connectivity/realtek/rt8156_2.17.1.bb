SUMMARY = "A kernel module for Realtek RTL8152/RTL8153/RTL8156 Based USB Ethernet Adapters"
HOMEPAGE = "https://www.realtek.com"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"

inherit module

PR = "r1"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/r8152.git;protocol=https;branch=master;destsuffix=s"
UNPACKDIR = "${WORKDIR}/u"

S = "${UNPACKDIR}/s"

EXTRA_OEMAKE = "KDIR=${STAGING_KERNEL_DIR}"

require kcflags.inc

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" modules
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
    install -m 0644 ${S}/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}

