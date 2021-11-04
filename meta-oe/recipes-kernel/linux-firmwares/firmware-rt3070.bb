LICENSE = "CLOSED"
require conf/license/license-close.inc

SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

SRC_URI = "git://github.com/mdamt/linux-firmware.git;protocol=https;branch=master"
SRC_URI = "git://github.com/BjornLee/linux-firmware.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

do_install() {
        install -d ${D}${nonarch_base_libdir}/firmware
        install -m 0644 rt3070.bin ${D}${nonarch_base_libdir}/firmware/
}

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"
RDEPENDS:${PN} = "firmware-rt2870"

inherit allarch
