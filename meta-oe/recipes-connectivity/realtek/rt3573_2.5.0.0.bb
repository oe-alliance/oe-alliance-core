SUMMARY = "Ralink 3573 v2.5.0.0"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://iwpriv_usage.txt;md5=8876ae2c103446a442658f1cc2a01b76"

PR = "r7"

inherit module

SRC_URI = "http://code-ini.com/software/mirror/20120911_RT3573_Linux_STA_v2.5.0.0_Rev3_DPO.tar.gz"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/20120911_RT3573_Linux_STA_v2.5.0.0_Rev3_DPO/"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/os/linux/rt3573sta.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}/etc/Wireless/RT3573STA
    install -m 0644 ${S}/RT2870STA.dat ${D}/etc/Wireless/RT3573STA/RT3573STA.dat
    install -m 0644 ${S}/RT2870STACard.dat ${D}/etc/Wireless/RT3573STA/RT3573STACard.dat
}

SRC_URI[md5sum] = "58a3814c3d0d7bf81f81a631e5cfc9f5"
SRC_URI[sha256sum] = "d98a3113673b1ad0d7f71203f1aecb1d870417536924e489233aec1819c91d9e"

FILES_${PN}_append = "${sysconfdir}/Wireless"
