SUMMARY = "Gigablue Drivers for AIC8800 for ${MACHINEBUID}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20241011"

KV = "4.1.20-1.9"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI  = "https://source.mynonpublic.com/gigablue/drivers/aic8800-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/kernel/drivers/net/wireless/aic8800/aic_load_fw
    install -m 0644 ${UNPACKDIR}/aic_load_fw.ko ${D}/lib/modules/${KV}/kernel/drivers/net/wireless/aic8800/aic_load_fw/aic_load_fw.ko
    install -d ${D}/lib/modules/${KV}/kernel/drivers/net/wireless/aic8800/aic8800_fdrv
    install -m 0644 ${UNPACKDIR}/aic8800_fdrv.ko ${D}/lib/modules/${KV}/kernel/drivers/net/wireless/aic8800/aic8800_fdrv/aic8800_fdrv.ko
    install -d ${D}/lib/modules/${KV}/kernel/drivers/bluetooth
    install -m 0644 ${UNPACKDIR}/aic_btusb.ko ${D}/lib/modules/${KV}/kernel/drivers/bluetooth/aic_btusb.ko
    install -d ${D}/${sysconfdir}/modules-load.d
    echo aic_load_fw > ${D}/${sysconfdir}/modules-load.d/aic_load_fw.conf
    echo aic8800_fdrv > ${D}/${sysconfdir}/modules-load.d/aic8800_fdrv.conf
    echo aic_btusb.ko > ${D}/${sysconfdir}/modules-load.d/aaic_btusb.conf
}

FILES:${PN} += "${sysconfdir} /lib/modules/${KV}"

SRC_URI[md5sum] = "afa9d00a1aaaf4bc9e48ec3e7c96cd11"
SRC_URI[sha256sum] = "c32f2094c1a5f8ecc6602b42424a0d3dec9adeac41dd5fc556e6cc2502024408"
