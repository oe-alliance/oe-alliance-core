SUMMARY = "Realtek rtw8852cu"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b7e6779b3b112ee657a10f5a3e1a4beb"

inherit module

PR = "r2"
SRCREV = "${AUTOREV}"
# Use short destsuffix — the driver has ~370 object files whose absolute paths
# overflow ARG_MAX during kbuild linking on machines with long MACHINE names.
SRC_URI = "git://github.com/oe-alliance-drivers/rtl8852cu.git;protocol=https;branch=master;destsuffix=s"

# The default unpack directory "sources" costs six more characters on every
# object path, and this driver has several hundred of them.
UNPACKDIR = "${WORKDIR}/u"

S = "${UNPACKDIR}/s"

# WPA3-SAE; the driver leaves this path disabled unless we ask for it
EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR} LINUX_SRC=${STAGING_KERNEL_DIR} KDIR=${STAGING_KERNEL_DIR} \
    USER_EXTRA_CFLAGS=-DCONFIG_KERNEL_PATCH_EXTERNAL_AUTH"

# kbuild links every object in one shell command, and an absolute M puts the
# full work path in front of each of them. Relative keeps that command short
# enough on machines whose name makes the path long.
EXTRA_OEMAKE:append = " M=${@os.path.relpath(d.getVar('S'), d.getVar('STAGING_KERNEL_DIR'))}"

require kcflags.inc

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8852cu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}
