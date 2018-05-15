include conf/license/license-gplv2.inc
SUMMARY = "Ralink 3070"
HOMEPAGE = "http://eng.ralinktech.com.tw/support.php?s=2"
SECTION = "kernel/modules"
#LICENSE = "GPLv2"

PR = "r3"

inherit module

# Original URL needs a click-wrap license.
SRC_URI = "http://www.penguin.cz/~utx/hardware/Ralink_3071/DPO_RT3070_LinuxSTA_V${PV}_20100604.tar.bz2 \
    file://makefile.patch \
    file://rt3070sta-2.3.0.4-ra-to-wlan.patch \
    file://rt3070sta-2.3.0.4-add-vendor.patch \
    "

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/DPO_RT3070_LinuxSTA_V${PV}_20100604"

# Source is in .tar.tar.bz2 format.
do_unpack() {
    mkdir -p ${WORKDIR}
    cd ${WORKDIR}
    bzip2 -dc %s ${DL_DIR}/DPO_RT3070_LinuxSTA_V${PV}_20100604.tar.bz2 | tar x --no-same-owner -O -f - | tar x --no-same-owner -f -
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/os/linux/rt3070sta.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}/etc/Wireless/RT2870STA
    install -m 0644 ${S}/RT2870STA.dat ${D}/etc/Wireless/RT2870STA
    install -d ${D}${base_libdir}/firmware
    install -m 0644 ${S}/common/*.bin ${D}${base_libdir}/firmware/
    install -d ${D}/etc/modutils
    echo rt3070sta > ${D}/etc/modutils/rt3070
}

PACKAGES =+ "${PN}-firmware"
FILES_${PN}-firmware = "/etc/Wireless ${base_libdir}/firmware"

RDEPENDS_${PN} = "${PN}-firmware"

SRC_URI[md5sum] = "bbfa7a278ae8fa0208499aee048ed1ed"
SRC_URI[sha256sum] = "df33f63f5396c85ab755bdb9d6c99d40ebfb30f50bd554a1fd6d7a3fb6095db4"
