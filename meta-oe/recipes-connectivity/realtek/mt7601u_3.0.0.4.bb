SUMMARY = "mediatek 7601 v3.0.0.4"
HOMEPAGE = "www.mediatek.com"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://iwpriv_usage.txt;md5=8876ae2c103446a442658f1cc2a01b76"

PR = "r7"

inherit module

SRC_URI = "http://source.mynonpublic.com/DPO_MT7601U_LinuxSTA_3.0.0.4_20130913c.zip \
          file://mt7601u.patch \
          file://remove_linux_2_4_compability.patch \
          "

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/MT7601U/"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/os/linux/mt7601Usta.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}${sysconfdir}/Wireless/MT7601U
    install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/MT7601U/MT7601U.dat
    install -m 0644 ${S}/RT2870STACard.dat ${D}${sysconfdir}/Wireless/MT7601U/MT7601UCard.dat
}

SRC_URI[md5sum] = "0b6d799d007de1594d8ae5bd34165341"
SRC_URI[sha256sum] = "43b3814b318d8baeab5138bbdb586461f94a52151135f55185a4b3b4c0ff2fe3"

FILES_${PN}_append = "${sysconfdir}/Wireless"

