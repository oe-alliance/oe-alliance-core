SUMMARY = "Ralink 8812AU/8821AU v4.2.2"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifcfg-wlan0;md5=a84acae65af4b2d44d5035aa9f63cd85"

inherit module

PR = "r7"

MACHINE_KERNEL_PR_append = ".1"

SRC_URI = "http://source.mynonpublic.com/rtl8812AU_8821AU_linux-4.2.3-20160329.zip"

SRC_URI_sh4 += "file://CONFIG_IOCTL_CFG80211.patch"

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
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8812au.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

}

SRC_URI[md5sum] = "270be04c3237e09d232e1e38059a9fae"
SRC_URI[sha256sum] = "f76ee751a8bd4627c6c6dc27c865853297e7cbfad864af5bb300defe87ecbbff"

