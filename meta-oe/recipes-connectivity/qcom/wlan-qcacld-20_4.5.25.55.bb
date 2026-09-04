DESCRIPTION = "qcacld-2.0 module.bbclass mechanism."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://Android.mk;md5=235cc8d87e0fb1c956be4af0d07074fb"
CAF_MIRROR = "https://source.codeaurora.org/external/wlan"

inherit module

COMPATIBLE_MACHINE = "osmio4k|osmio4kplus"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance-drivers/qcacld-2.0.git;protocol=https;branch=master;destsuffix=s"

S = "${UNPACKDIR}/s"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/wlan.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
}

python do_package:prepend() {
    d.appendVar('PKGV', '-')
    d.appendVar('PKGV', d.getVar("KERNEL_VERSION", True).split("-")[0])
}

