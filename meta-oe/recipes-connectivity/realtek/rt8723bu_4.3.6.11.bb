SUMMARY = "Realtek 8723BU WIFI"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README.md;md5=7967f55fbefa3330942afef996a272db"

inherit module

PR = "r1"

MACHINE_KERNEL_PR_append = ".0"

SRC_URI = "http://source.mynonpublic.com/rtl8723bu_v4.3.6.11_12942.20141204_BTCOEX20140507-4E40.zip \
    file://rt8723bu-gcc5.patch \
"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"
S = "${WORKDIR}/"

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
    install -m 0644 ${S}/8723bu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

SRC_URI[md5sum] = "dee476779b809962be52dc884a8947db"
SRC_URI[sha256sum] = "8948749f706cb1937511a0b6af0d1d656e182146b66fc6758e5d522643dea630"


