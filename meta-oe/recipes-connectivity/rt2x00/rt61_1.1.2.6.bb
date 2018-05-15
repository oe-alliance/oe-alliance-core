SUMMARY = "Driver for Ralink RT2501PCI/mPCI/CB (RT61:RT2561/RT2561S/RT2661)"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=d517aa8db7935eee411104c5be1b2ed9"

inherit module

PR = "r4"
MACHINE_KERNEL_PR_append = ".2"

SRC_URI = "http://source.mynonpublic.com/ebox/2010_0825_RT61_Linux_STA_v${PV}.tar.bz2 \
              file://makefile_rt61_1.1.2.6.patch \
"


SRC_URI[md5sum] = "433192a7f42557a33c8255c86141882d"
SRC_URI[sha256sum] = "303b0d96ec2fcb03b874b32bedc248359c639ae1bb1cfccf52d5bbb72ca467d5"

FILES_${PN} += " ${nonarch_base_libdir}/firmware/"

S = "${WORKDIR}/2010_0825_RT61_Linux_STA_v${PV}/Module"

EXTRA_OEMAKE = "LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/rt61.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/*.bin ${D}${nonarch_base_libdir}/firmware/
    install -d ${D}/${sysconfdir}/modules-load.d
    echo rt61 >> ${D}/${sysconfdir}/modules-load.d/rt61.conf
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
