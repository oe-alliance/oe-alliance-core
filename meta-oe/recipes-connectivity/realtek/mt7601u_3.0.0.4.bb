SUMMARY = "mediatek 7601 v3.0.0.4"
HOMEPAGE = "www.mediatek.com"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://iwpriv_usage.txt;md5=8876ae2c103446a442658f1cc2a01b76"

inherit module

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/mt7601u.git;protocol=https;branch=master;destsuffix=s"
EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

require kcflags.inc

S = "${UNPACKDIR}/s"
do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/os/linux/mt7601Usta.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}${sysconfdir}/Wireless/MT7601U
    install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/MT7601U/MT7601U.dat
    install -m 0644 ${S}/RT2870STACard.dat ${D}${sysconfdir}/Wireless/MT7601U/MT7601UCard.dat
}

FILES:${PN}:append = "${sysconfdir}/Wireless"

