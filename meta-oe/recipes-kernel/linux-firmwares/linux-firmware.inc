LICENSE = "CLOSED"
require conf/license/license-close.inc

SRC_URI = "git://github.com/mdamt/linux-firmware.git;protocol=https;branch=master"
SRC_URI = "git://github.com/BjornLee/linux-firmware.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch
