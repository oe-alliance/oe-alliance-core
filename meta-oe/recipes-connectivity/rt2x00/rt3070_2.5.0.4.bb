SUMMARY = "Driver for Ralink RT8070/3070/3370/5370/5372 USB 802.11abgn WiFi sticks"
SECTION = "kernel/modules"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://os/linux/rt_linux.c;endline=25;md5=21ed2a5918a3062a6c0323ef549f0803"

PR = "r2"

inherit module

SRC_URI = " \
    http://source.mynonpublic.com/ini/2011_0719_RT3070_RT3370_RT5370_RT5372_Linux_STA_V${PV}_DPO.tar.gz \
    file://makefile.patch \
    file://config.patch \
    file://remove_linux_2_4_compability.patch \
"
SRC_URI[md5sum] = "c314f126ba47fac563988b08b5554d82"
SRC_URI[sha256sum] = "c1555c6e8b042eb50579f5dae379d8dab3b2cde20e2e443a6b056d4b7812b3fe"

S = "${WORKDIR}/2011_0719_RT3070_RT3370_RT5370_RT5372_Linux_STA_V${PV}_DPO"

inherit module

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/*sta${KERNEL_OBJECT_SUFFIX} ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}${sysconfdir}/Wireless/RT2870STA
    install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/RT2870STA
}

FILES_${PN}_append = "${sysconfdir}/Wireless"
