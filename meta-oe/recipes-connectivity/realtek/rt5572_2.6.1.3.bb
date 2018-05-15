SUMMARY = "Ralink 2870/3070/8070/3370/3572/5370/5372/5572 v2.6.1.3"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://iwpriv_usage.txt;md5=8876ae2c103446a442658f1cc2a01b76"

PR = "r7"

inherit module

SRC_URI = "http://source.mynonpublic.com/ini/rt5572_2.6.1.4.tar.gz \
    file://remove_linux_2_4_compability.patch"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/RT5572/"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/os/linux/rt5572sta.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}${sysconfdir}/Wireless/RT5572STA
    install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/RT5572STA/RT5572STA.dat
    install -m 0644 ${S}/RT2870STACard.dat ${D}${sysconfdir}/Wireless/RT5572STA/RT5572STACard.dat
}

SRC_URI[md5sum] = "88d11654985e8da2101bc92d4fa00340"
SRC_URI[sha256sum] = "50bc80038a1a691bdcce426e95e9480f1d424fbc99690dac91a51c67b49090ea"

FILES_${PN}_append = "${sysconfdir}/Wireless"

