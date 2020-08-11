SUMMARY = "Ralink 8812AU/8821AU v5.1.5"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

inherit module

SRC_URI = "http://source.mynonpublic.com/rtl8812AU-driver-5.1.5-20170914.zip \
    file://remove-efuse-config-file.patch \
    file://0001-add-linux-kernel-4.14-support.patch \
    file://0001-add-linux-kernel-4.15-support.patch \
    file://0001-add-linux-kernel-4.19-support.patch \
    file://0001-add-linux-kernel-4.20-support.patch \
    file://0001-add-linux-kernel-5.0-support.patch \
    file://0001-add-linux-kernel-5.1-support.patch \
    file://0001-add-linux-kernel-5.2-support.patch \
    file://0001-add-linux-kernel-5.6-support.patch \
    file://0001-add-linux-kernel-5.8-support.patch \
"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/rtl8812AU"

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
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8812au.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

do_package_qa() {
}

SRC_URI[md5sum] = "5bb6be1683428b09bd3e4deb1e2c7622"
SRC_URI[sha256sum] = "f3ac257ea4cdadfc9da8e275bfc19b3bd720f17d72cf5d23231ba7fb73bc28b3"

