SUMMARY = "Driver for Ralink RT8070/3070/3370/5370/5372 USB 802.11abgn WiFi sticks"
SECTION = "kernel/modules"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://os/linux/rt_linux.c;endline=25;md5=21ed2a5918a3062a6c0323ef549f0803"
PR = "r3"

SRC_URI = " \
        http://sources.dreamboxupdate.com/download/sources/2011_0719_RT3070_RT3370_RT5370_RT5372_Linux_STA_V${PV}_DPO.tar.bz2 \
        file://makefile.patch \
        file://config.patch \
"
SRC_URI[md5sum] = "8ea0d247ac5881de1cb4c113ebf65724"
SRC_URI[sha256sum] = "e732d6b114137aa0badf46281d25d442278639d798735317b0061d3ae573593e"

S = "${WORKDIR}/2011_0719_RT3070_RT3370_RT5370_RT5372_Linux_STA_V${PV}_DPO"

inherit module

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
        install -m 0644 ${S}/*sta${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
        install -d ${D}${sysconfdir}/Wireless/RT2870STA
        install -m 0644 ${S}/RT2870STA.dat ${D}${sysconfdir}/Wireless/RT2870STA
}
