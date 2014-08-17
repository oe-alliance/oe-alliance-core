SUMMARY = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://avl_xtrend_blindscan"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

PV = "1.1"
PR = "r3"

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/usr/bin/avl_xtrend_blindscan" "${D}/${bindir}"
}

INHIBIT_PACKAGE_STRIP = "1"
