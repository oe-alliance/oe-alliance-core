SUMMARY = "Firmware for QCA9377"
inherit allarch

LIC_FILES_CHKSUM = "file://LICENSE.qca_firmware;md5=2a397c0e988f4c52d3d526133b617c8d"
LICENSE = "Proprietary"

PR = "r0"

SRC_URI = "http://source.mynonpublic.com/xcore/firmware-qca9377-linux_linux_proprietary_firmware.zip"
SRC_URI[md5sum] = "beb7ca4a7dd6ee268627b3feca71c8a8"
SRC_URI[sha256sum] = "f53ad83fdb5be35d2b22028f6e5c5c5d0596a4b3db31a09677711663e0732890"

S = "${WORKDIR}/firmware-qca9377-linux_linux_proprietary_firmware"

do_compile() {
    :
}

do_install() {
    install -d  ${D}${nonarch_base_libdir}/firmware/
    cp -a * ${D}${nonarch_base_libdir}/firmware/
}

FILES_${PN} += "${nonarch_base_libdir}/firmware/*"
