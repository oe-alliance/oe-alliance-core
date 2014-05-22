SUMMARY = "Realtek 8723A v1.0"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;md5=b60d4f09fa0f027334487c4cb2fd8117"

inherit module

PR = "r11"

MACHINE_KERNEL_PR_append = ".0"

SRC_URI = "http://code-ini.com/software/mirror/rtl8723A_WiFi_linux_v4.1.6_7336.20130431.tar.gz"

inherit module

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR}"
S = "${WORKDIR}/rtl8723A_WiFi_linux_v4.1.6_7336.20130431"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8723au.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

SRC_URI[md5sum] = "6c96ed8f0c3c5e630c21eb8016ee857d"
SRC_URI[sha256sum] = "5b657a1a49f0e55a81ba36d179ee30f533983ab94bc4e48c87136c31c0b9029f"

