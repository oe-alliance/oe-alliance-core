SUMMARY = "Realtek 8723D SDIO or SPI WiFi"
HOMEPAGE = "https://www.realtek.com/"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

DEPENDS = "bc-native"

SRC_URI = "git://github.com/oe-alliance-drivers/rtl8723ds.git;protocol=https;branch=master;destsuffix=s"

S = "${UNPACKDIR}/s"

SRCREV = "${AUTOREV}"

inherit module

PR = "r1"

# WPA3-SAE; the driver leaves this path disabled unless we ask for it.
EXTRA_OEMAKE = 'KSRC="${STAGING_KERNEL_BUILDDIR}" USER_EXTRA_CFLAGS="-Wno-date-time -DCONFIG_KERNEL_PATCH_EXTERNAL_AUTH" CONFIG_RTW_DEBUG=n'

require kcflags.inc

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8723ds.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
