SUMMARY = "Realtek Bluetooth USB driver"
HOMEPAGE = "https://www.realtek.com/"
require conf/license/license-close.inc

inherit module
SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/rtk-btusb.git;protocol=https;branch=master;destsuffix=s"

S = "${UNPACKDIR}/s"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

require kcflags.inc

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth
    install -m 0644 ${S}/rtk_btusb.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth
}
