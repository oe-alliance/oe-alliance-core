SUMMARY = "ASIX AX88179_178A USB 3.0/2.0 Gigabit Ethernet Network Adapter"
HOMEPAGE = "http://www.asix.com.tw/"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"

inherit module

LIC_FILES_CHKSUM = "file://readme;endline=19;md5=f87a675da5e11ab9def922704bdda58b"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/ax88179.git;protocol=https;branch=master;destsuffix=s"
S = "${UNPACKDIR}/s"
EXTRA_OEMAKE = "KDIR=${STAGING_KERNEL_DIR}"

export KCFLAGS += " -std=gnu17"

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" modules
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
    install -m 0644 ${S}/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}

