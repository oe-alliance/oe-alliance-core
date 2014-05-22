SUMMARY = "Ralink 2870/3070/8070/3370/3572/5370/5372/5572 v2.6.1.3"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://iwpriv_usage.txt;md5=8876ae2c103446a442658f1cc2a01b76"

PR = "r4"

inherit module

SRC_URI = "http://code-ini.com/software/mirror/rt5572_2.6.1.3.tar.gz"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/RT5572/"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/os/linux/rt5572sta.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}${sysconfdir}/Wireless/RT5572STA
    install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/RT5572STA/RT5572STA.dat
    install -m 0644 ${S}/RT2870STACard.dat ${D}${sysconfdir}/Wireless/RT5572STA/RT5572STACard.dat
}

SRC_URI[md5sum] = "73793cd42bfbbf3a3be7eb687de79138"
SRC_URI[sha256sum] = "351ae0bdee6a45f0ed650bbbf378df15204f37ad5534bcc1bf9433eb06b86ca7"

FILES_${PN}_append = "${sysconfdir}/Wireless"
