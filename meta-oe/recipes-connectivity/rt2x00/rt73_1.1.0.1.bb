SUMMARY = "Driver for Ralink rt73 USB 802.11g WiFi sticks"
HOMEPAGE = "http://www.ralinktech.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../../COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

RREPLACES_${PN} = "rt73-k2wrlz"
RCONFLICTS_${PN} = "rt73-k2wrlz"

MACHINE_KERNEL_PR_append = ".2"

SRC_URI = "http://downloads.pli-images.org/misc/2008_0506_RT73_Linux_STA_Drv1.1.0.1.tar.bz2 \
           file://changeiface.patch \
           file://COPYING"

inherit module
 
S = "${WORKDIR}/2008_0506_RT73_Linux_STA_Drv1.1.0.1/Module/"
 
EXTRA_OEMAKE = "KERNDIR=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
    oe_runmake 'MODPATH={D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net' \
        'KERNEL_SOURCE=${STAGING_KERNEL_DIR}' \
        'LINUX_SRC=${STAGING_KERNEL_DIR}' \
        'KDIR=${STAGING_KERNEL_DIR}' \
        'KERNDIR=${STAGING_KERNEL_DIR}' \
        'KERNEL_VERSION=${KERNEL_VERSION}' \
        'CC=${KERNEL_CC}' \
        'LD=${KERNEL_LD}'
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/
    install -m 0644 rt73${KERNEL_OBJECT_SUFFIX} ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/
    install -d ${D}/etc/modutils
    echo rt73 > ${D}/etc/modutils/rt73
}

SRC_URI[md5sum] = "3e08bf734b740e83391fda571c8e9ecd"
SRC_URI[sha256sum] = "932b9c127992fd7985e4054d91120164e54f7132fa457b06fdcd30307e5b70f9"
