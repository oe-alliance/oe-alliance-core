SUMMARY = "Driver for Realtek USB wireless devices"
HOMEPAGE = "http://www.realtek.com/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://os_dep/linux/os_intfs.c;endline=19;md5=72c75de415f1e8a42587d170459677e2"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/rtl8192cu.git;protocol=https;branch=master;destsuffix=s"
S = "${UNPACKDIR}/s"
inherit module siteinfo

EXTRA_OEMAKE = "CONFIG_RTL8192CU=m KDIR=${STAGING_KERNEL_DIR}"

require kcflags.inc

do_configure() {
        sed -e "s/^CONFIG_PLATFORM_I386_PC.*=.*y/EXTRA_CFLAGS += -DCONFIG_${@oe.utils.conditional('SITEINFO_ENDIANNESS', 'le', 'LITTLE', 'BIG', d)}_ENDIAN/" -i Makefile
}
do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" modules 
}

do_install() {
        install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
        install -m 0644 ${S}/8192cu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
