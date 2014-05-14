SUMMARY = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "http://www.et-view.com/data/enigma2-plugin-systemplugins-blindscan_2011-04-15_mipsel.ipk"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

PV = "1.0"
PR = "r2"

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/usr/bin/avl_xtrend_blindscan" "${D}/${bindir}"
}

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI[md5sum] = "19abe3fcaf21ff22ea2e78a4fe6c3026"
SRC_URI[sha256sum] = "20339a4cf77237b515b667afb59aea1fccf1d768ef9f0daf6c8901361a3d5bb6"
