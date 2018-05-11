SUMMARY = "Ralink 88x2BU v1.0.0.9"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ffa10f40b98be2c2bc9608f56827ed23"

inherit module

PR = "r2"

SRC_URI = "http://source.mynonpublic.com/rtl8822bu-driver-1.0.0.9-20180511.zip"

SRC_URI_append_sh4 = " file://fix_sh4_build.patch"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/rtl8822bu"

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
    install -m 0644 ${S}/88x2bu.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

do_package_qa() {
}

SRC_URI[md5sum] = "8b9ed40ef61ed8e1c037dfe4c337c880"
SRC_URI[sha256sum] = "db3ac4674662793aae511dcd53ed1628edc48de18d8e3ff854f59d1342ad6b7d"
