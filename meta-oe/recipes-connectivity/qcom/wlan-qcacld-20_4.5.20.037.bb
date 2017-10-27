DESCRIPTION = "qcacld-2.0 module.bbclass mechanism."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=d41d8cd98f00b204e9800998ecf8427e"
CAF_MIRROR = "https://www.codeaurora.org/cgit/external/wlan"

inherit module


SRC_URI = "${CAF_MIRROR}/qcacld-2.0/snapshot/qcacld-2.0-${PV}.tar.gz \
    file://qcacld-2.0-add-4.11-support.patch \
"

#file://qcacld-2.0-add-4.12-support.patch
#file://qcacld-2.0-add-4.12-support-2.patch


S = "${WORKDIR}/qcacld-2.0-${PV}"

inherit module

do_install() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/wlan.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
}

python do_package_prepend() {
    d.appendVar('PKGV', '-')
    d.appendVar('PKGV', d.getVar("KERNEL_VERSION", True).split("-")[0])
}

SRC_URI[md5sum] = "8b0b63dca02d3bf42f8a81ee84f0a4ca"
SRC_URI[sha256sum] = "36c086e25315865afb25387d00fc562a2620654ddb33e908302f204aac917d77"
