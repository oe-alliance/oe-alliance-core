SUMMARY = "Driver for Realtek 871x series USB 802.11b/g/n WiFi stick"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

PR = "r3"

inherit module

PR = "r1"

SRC_URI = "http://source.mynonpublic.com/rtl871x-01112013.tar.gz \
           file://rtl871x-gcc5.patch \
           file://COPYING"

SRC_URI[md5sum] = "5faf6a314a63e1f3be9b227157bb76a1"
SRC_URI[sha256sum] = "e0fd28318dc0b2846e869d69a95b0a7a7d2fe182964448e4e1d74bdd12e4418f"

S = "${WORKDIR}/rtl871x"

EXTRA_OEMAKE = "KERNDIR=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake 'MODPATH={D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net' \
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
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/
    install -m 0644 8712u${KERNEL_OBJECT_SUFFIX} ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/
    install -d ${D}/etc/modutils
    echo 8712u > ${D}/etc/modutils/wlan8712u
}

