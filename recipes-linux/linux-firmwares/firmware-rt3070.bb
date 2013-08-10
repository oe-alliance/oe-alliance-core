LICENSE = "CLOSED"

SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

SRC_URI = "git://github.com/mdamt/linux-firmware.git;protocol=git"
SRC_URI = "git://github.com/BjornLee/linux-firmware.git;protocol=git"

S = "${WORKDIR}/git"

do_install() {
        install -d ${D}${base_libdir}/firmware
        install -m 0644 rt3070.bin ${D}/${base_libdir}/firmware/
}

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"
RDEPENDS_${PN} = "firmware-rt2870"

PACKAGE_ARCH = "all"
