DESCRIPTION = "Driver for Ralink mt7610u USB 802.11a/b/g/n/ac WiFi sticks"
HOMEPAGE = "www.mediatek.com"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://iwpriv_usage.txt;md5=8876ae2c103446a442658f1cc2a01b76"

inherit module

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/mt7610u.git;protocol=https;branch=master;destsuffix=s"

S = "${UNPACKDIR}/s"
EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

require kcflags.inc

do_install() {
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
	install -d ${D}${sysconfdir}/Wireless/mt7610uSTA
	install -m 0644 ${S}/os/linux/mt7650u_sta.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/mt7650u_sta.ko
	install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/mt7610uSTA/mt7610uSTA.dat
	install -m 0644 ${S}/RT2870STACard.dat ${D}${sysconfdir}/Wireless/mt7610uSTA/mt7610uSTACard.dat
	install -m 0644 ${S}/conf/SingleSKU.dat ${D}${sysconfdir}/Wireless/mt7610uSTA/SingleSKU.dat
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES:${PN} += "${sysconfdir}/Wireless/mt7610uSTA/mt7610uSTACard.dat ${sysconfdir}/Wireless/mt7610uSTA/mt7610uSTA.dat ${sysconfdir}/Wireless/mt7610uSTA/SingleSKU.dat"

