SUMMARY = "Realtek 8723D SDIO or SPI WiFi"
HOMEPAGE = "https://www.realtek.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

SRC_URI = "git://github.com/edision-open/RTL8723DS_WiFi_linux.git"

SRCREV = "94eef3f7cb762b7309824be6cd2b6af75ac80bbd"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE = 'KSRC="${STAGING_KERNEL_BUILDDIR}" USER_EXTRA_CFLAGS="-Wno-date-time" CONFIG_RTW_DEBUG=n'

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8723ds.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
