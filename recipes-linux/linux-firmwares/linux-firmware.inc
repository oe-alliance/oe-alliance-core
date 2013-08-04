LICENSE = "CLOSED"

SRC_URI = "git://github.com/mdamt/linux-firmware.git;protocol=git"
SRC_URI = "git://github.com/BjornLee/linux-firmware.git;protocol=git"

S = "${WORKDIR}/git"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

PACKAGE_ARCH = "all"
