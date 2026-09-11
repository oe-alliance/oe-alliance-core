SUMMARY = "Gigablue Drivers for AIC8800 for ${MACHINEBUID}"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

inherit module-base

KV = "${KERNEL_VERSION}"

PV = "5.0"
PR = "r0"

SRC_URI = "git://github.com/oe-alliance-drivers/aic8800.git;protocol=https;branch=master;destsuffix=s"
SRCREV = "${AUTOREV}"

UNPACKDIR = "${WORKDIR}/u"
S = "${UNPACKDIR}/s/src/USB/driver_fw/drivers"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# Selects /lib/firmware over the Android path for the firmware lookup.
EXTRA_OEMAKE = "CONFIG_PLATFORM_UBUNTU=y"
# WPA3-SAE: the driver gates external auth on kernel 4.17, and our kernels carry
# the cfg80211 backport instead.
export KCFLAGS += " -std=gnu17 -fgnu89-inline -Wno-error -DCONFIG_WPA3_FOR_OLD_KERNEL"

do_compile() {
    oe_runmake -C ${STAGING_KERNEL_BUILDDIR} M=${S}/aic8800 modules
    oe_runmake -C ${STAGING_KERNEL_BUILDDIR} M=${S}/aic_btusb modules
}

do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/kernel/drivers/net/wireless/aic8800/aic_load_fw
    install -m 0644 ${S}/aic8800/aic_load_fw/aic_load_fw.ko ${D}/lib/modules/${KV}/kernel/drivers/net/wireless/aic8800/aic_load_fw/aic_load_fw.ko
    install -d ${D}/lib/modules/${KV}/kernel/drivers/net/wireless/aic8800/aic8800_fdrv
    install -m 0644 ${S}/aic8800/aic8800_fdrv/aic8800_fdrv.ko ${D}/lib/modules/${KV}/kernel/drivers/net/wireless/aic8800/aic8800_fdrv/aic8800_fdrv.ko
    install -d ${D}/lib/modules/${KV}/kernel/drivers/bluetooth
    install -m 0644 ${S}/aic_btusb/aic_btusb.ko ${D}/lib/modules/${KV}/kernel/drivers/bluetooth/aic_btusb.ko
    install -d ${D}/${sysconfdir}/modprobe.d
    echo "options aic_load_fw aicwf_dbg_level=1" > ${D}/${sysconfdir}/modprobe.d/aic8800.conf
    echo "options aic8800_fdrv aicwf_dbg_level=1" >> ${D}/${sysconfdir}/modprobe.d/aic8800.conf
    install -d ${D}/${sysconfdir}/modules-load.d
    echo aic_load_fw > ${D}/${sysconfdir}/modules-load.d/aic_load_fw.conf
    echo aic8800_fdrv > ${D}/${sysconfdir}/modules-load.d/aic8800_fdrv.conf
    echo aic_btusb.ko > ${D}/${sysconfdir}/modules-load.d/aaic_btusb.conf
}

FILES:${PN} += "${sysconfdir} /lib/modules/${KV}"
