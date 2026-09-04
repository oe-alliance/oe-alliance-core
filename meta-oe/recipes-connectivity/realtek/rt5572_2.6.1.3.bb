SUMMARY = "Ralink 2870/3070/8070/3370/3572/5370/5372/5572 v2.6.1.3"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://iwpriv_usage.txt;md5=8876ae2c103446a442658f1cc2a01b76"

inherit module

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/rt5572.git;protocol=https;branch=master;destsuffix=s"
EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

require kcflags.inc

S = "${UNPACKDIR}/s"
do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/os/linux/rt5572sta.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}${sysconfdir}/Wireless/RT5572STA
    install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/RT5572STA/RT5572STA.dat
    install -m 0644 ${S}/RT2870STACard.dat ${D}${sysconfdir}/Wireless/RT5572STA/RT5572STACard.dat
}

FILES:${PN}:append = "${sysconfdir}/Wireless"

