SUMMARY = "Driver for Realtek USB wireless devices"
HOMEPAGE = "http://www.realtek.com/"
SECTION = "kernel/modules"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://os_dep/linux/os_intfs.c;endline=19;md5=f8d10a6bd2fdfa240c0634a7c660c57f"

PR = "r7"

inherit module

SRC_URI = "http://code-ini.com/software/mirror/rtl8188C_8192C_usb_linux_v3.4.4_4749.20140525.tar.gz \
    file://additional_productids.patch \
    file://add-3.8-support.patch \
    file://rt8192cu_procfs.patch \
    "

S = "${WORKDIR}/rtl8188C_8192C_usb_linux_v3.4.4_4749.20121105"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR}"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8192cu.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}

SRC_URI[md5sum] = "7775a977d4f1dd7df321a6576c02e0dd"
SRC_URI[sha256sum] = "f503fe4ef1e7bdd412e9ffc7049dd8f7e2213092d3307b48d748d0a66e2878d9"
