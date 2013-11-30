DESCRIPTION = "Driver for Realtek USB wireless devices"
HOMEPAGE = "http://www.realtek.com/"
SECTION = "kernel/modules"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://os_dep/linux/os_intfs.c;endline=19;md5=f8d10a6bd2fdfa240c0634a7c660c57f"

inherit module machine_kernel_pr

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/rtl8188C_8192C_usb_linux_v3.4.4_4749.20121105.tar.gz \
	file://additional_productids.patch \
	file://add-3.8-support.patch \
	file://rt8192cu_procfs.patch \
	"

S = "${WORKDIR}/rtl8188C_8192C_usb_linux_v3.4.4_4749.20121105"

MACHINE_KERNEL_PR_append = ".4"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR}"

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8192cu.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}

SRC_URI[md5sum] = "05755143f52d597733968608d7467796"
SRC_URI[sha256sum] = "08593a1c0fc946249fda0deb8cdb6200af42d02fc6a6ec58d99fe45f32c8faff"
