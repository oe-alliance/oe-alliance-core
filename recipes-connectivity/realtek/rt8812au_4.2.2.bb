DESCRIPTION = "Ralink 8812AU/8821AU v4.2.2"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

inherit module

PR = "r2"

MACHINE_KERNEL_PR_append = ".1"

SRC_URI = "http://source.mynonpublic.com/rtl8812AU_8821AU_linux_v4.2.2_7502.20130826_addl_IDs_added.tar.bz2 \
	file://rt8812au-procfs.patch \
	"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/rtl8812AU_8821AU_linux_v4.2.2_7502.20130517/"

do_install() {
	install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
	install -m 0644 ${S}/8812au.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

SRC_URI[md5sum] = "34a9e284a5a1a14572efed7476d55509"
SRC_URI[sha256sum] = "1a8f9a6e3245dfb163cea55a6197aeaccec1686ac5c919f610e26267e459aecb"

