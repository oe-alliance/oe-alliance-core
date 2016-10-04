SUMMARY = "Ralink 8812AU/8821AU v4.3.14"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=6061d24ec65e191716f64bb3fe580790"

inherit module

PR = "r9"

MACHINE_KERNEL_PR_append = ".1"

SRC_URI = "http://source.mynonpublic.com/rtl8812AU-driver-4.3.14.zip \
    file://rt8812au-gcc5.patch \
    file://0001-Add-support-for-kernels-4.8.patch \
"

SRC_URI_append_sh4 = "file://fix_sh4_build.patch"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

S = "${WORKDIR}/rtl8812AU-driver-4.3.14"

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

SRC_URI[md5sum] = "0f36c65f154971c3b305a1705f9e500f"
SRC_URI[sha256sum] = "e1ab86b4aca9ee599141d2d23dd5c989e0a6d004c8b87a487b370e80a4aba7e2"

