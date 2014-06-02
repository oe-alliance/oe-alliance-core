SUMMARY = "Ralink 8812AU/8821AU v4.2.2"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

inherit module

PR = "r4"

MACHINE_KERNEL_PR_append = ".1"

SRC_URI = "http://code-ini.com/software/mirror/rtl8812AU_8821AU_linux_v4.2.2_7502.20140602.tar.gz \
    file://rt8812au-procfs.patch \
    "

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/rtl8812AU_8821AU_linux_v4.2.2_7502.20140602/"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8812au.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

SRC_URI[md5sum] = "dd5085665501b0a3a5fbfc84dd4a6f37"
SRC_URI[sha256sum] = "98606139a9f7e741bc25e79f96fcbfe286e41010e1856b5e5511f68aba71068d"

