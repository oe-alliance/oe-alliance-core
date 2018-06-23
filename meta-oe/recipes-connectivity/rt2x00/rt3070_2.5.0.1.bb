include conf/license/license-gplv2.inc
SUMMARY = "Ralink RT5370"
HOMEPAGE = "http://eng.ralinktech.com.tw/support.php?s=2"
SECTION = "kernel/modules"

PR = "r3"

inherit module

SRC_URI = "http://source.mynonpublic.com/gigablue/wlan/RT3070_RT3370_RT5370_RT5372.tar.gz"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/RT3070_RT3370_RT5370_RT5372"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/os/linux/rt5370sta.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}/etc/Wireless/RT2870STA
    install -m 0644 ${S}/RT2870STA.dat ${D}/etc/Wireless/RT2870STA
    install -d ${D}/etc/modutils
    echo rt5370sta > ${D}/etc/modutils/rt5370
}

PACKAGES =+ "${PN}-firmware"
FILES_${PN}-firmware = "/etc/Wireless"

RDEPENDS_${PN} = "${PN}-firmware"

SRC_URI[md5sum] = "65e0d177c2b965f562b1accf63800008"
SRC_URI[sha256sum] = "c24f6273f4194ac94508e8eabbd76dda617b79fe1b8ece24319a72c999c6f4a6"
