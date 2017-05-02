SUMMARY = "Ralink 8812AU/8821AU v5.1.5"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

inherit module

PR = "r0"

SRC_URI = "http://source.mynonpublic.com/rtl8812AU-rtl8821AU-driver-5.1.5-20170406.zip \
    file://0001-add-linux-kernel-4.11-support.patch \
"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/rtl8812AU-rtl8821AU-driver-${PV}"

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake 'M={D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless' \
        'KERNEL_SOURCE=${STAGING_KERNEL_DIR}' \
        'LINUX_SRC=${STAGING_KERNEL_DIR}' \
        'KDIR=${STAGING_KERNEL_DIR}' \
        'KERNDIR=${STAGING_KERNEL_DIR}' \
        'KSRC=${STAGING_KERNEL_DIR}' \
        'KERNEL_VERSION=${KERNEL_VERSION}' \
        'KVER=${KERNEL_VERSION}' \
        'CC=${KERNEL_CC}' \
        'AR=${KERNEL_AR}' \
        'LD=${KERNEL_LD}'
}

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8812au.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

do_package_qa() {
}

SRC_URI[md5sum] = "317810f0b78b57b1fa3e16f0e8b62f68"
SRC_URI[sha256sum] = "267be7201abfe594332d79a70aac7ad499708248a9efac9bdae2fb5f4154d613"
