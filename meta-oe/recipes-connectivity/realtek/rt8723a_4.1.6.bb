SUMMARY = "Realtek 8723A v1.0"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;md5=b5b238c45bb0acc6b381f229a69d6b41"

inherit module

PR = "r14"

MACHINE_KERNEL_PR_append = ".0"

SRC_URI = "http://code-ini.com/software/mirror/rtl8723A_WiFi_linux_v4.1.6_7336.20140602.tar.gz"

inherit module

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"
S = "${WORKDIR}/rtl8723A_WiFi_linux_v4.1.6_7336.20140602"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8723au.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

SRC_URI[md5sum] = "a2370c98b521c779fd526abeb9c1b842"
SRC_URI[sha256sum] = "12fef9125e180b1da348ed3988d51a405d75c06752e0e4508541f1e6ff252534"

