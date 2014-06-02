SUMMARY = "ASIX AX88179_178A USB 3.0/2.0 Gigabit Ethernet Network Adapter"
HOMEPAGE = "http://www.asix.com.tw/"
SECTION = "kernel/modules"
LICENSE = "GPLv2"

PR = "r4"

inherit module

LIC_FILES_CHKSUM = "file://readme;endline=19;md5=f87a675da5e11ab9def922704bdda58b"

inherit module machine_kernel_pr

SRC_URI = "http://code-ini.com/software/mirror/AX88179_178A_LINUX_DRIVER_v1.4.1_SOURCE.tar.gz"

SRC_URI_append_dm500hd = " \
            file://dreambox.patch \
            "
SRC_URI_append_dm8000 = " \
            file://dreambox.patch \
            "
SRC_URI_append_dm7020hd = " \
            file://dreambox.patch \
            "
SRC_URI_append_dm7020hdv2 = " \
            file://dreambox.patch \
            "
SRC_URI_append_dm800se = " \
            file://dreambox.patch \
            "
SRC_URI_append_dm500hdv2 = " \
            file://dreambox.patch \
            "
SRC_URI_append_dm800sev2 = " \
            file://dreambox.patch \
            "
            
S = "${WORKDIR}/AX88179_178A_LINUX_DRIVER_v1.4.1_SOURCE"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR}"

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
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net
    install -m 0644 ${S}/*.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}

SRC_URI[md5sum] = "584c80be94c4d945cee30881c410b58d"
SRC_URI[sha256sum] = "e084fbe1b4de7b4dd703a92e643642e876d2757e8936619f99864e9ad6ffb582"
