LICENSE = "CLOSED"
require conf/license/license-close.inc

SRC_URI = "git://github.com/mdamt/linux-firmware.git;protocol=git"
SRC_URI = "git://github.com/BjornLee/linux-firmware.git;protocol=git"

S = "${WORKDIR}/git"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch
