SUMMARY = "Driver for Realtek USB wireless device 8192eu"
HOMEPAGE = "http://www.realtek.com/"
LICENSE = "GPLv2"
PR = "r2"
LIC_FILES_CHKSUM = "file://README.md;md5=fdff58b3f51b88c0d94687533380bf9b"

SRC_URI = "git://github.com/Mange/rtl8192eu-linux-driver.git \
    file://rtl8192eu-makefile.patch \
    file://rtl8192eu-autoconf.patch \
    file://rtl8192eu-gcc5.patch \
    file://CHECKSM_IPV6_H.patch \
    "

SRCREV = "0c80da7f67607bf5916a9def05cfc4099c098057"

S = "${WORKDIR}/git"

inherit module siteinfo

EXTRA_OEMAKE = "CONFIG_RTL8192EU=m"

do_configure() {
        sed -e "s/^CONFIG_PLATFORM_I386_PC.*=.*y/EXTRA_CFLAGS += -Wno-date-time -DCONFIG_${@base_conditional('SITEINFO_ENDIANNESS', 'le', 'LITTLE', 'BIG', d)}_ENDIAN/" -i Makefile
}
do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" modules 
}

do_install() {
        install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
        install -m 0644 ${S}/8192eu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}


