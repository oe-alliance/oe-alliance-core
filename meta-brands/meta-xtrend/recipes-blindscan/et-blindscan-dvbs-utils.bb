SUMMARY = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "http://www.xtrendet.net/enigma2-plugin-systemplugins-blindscan_2012-02-14_mipsel.zip"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

PV = "1.1"
PR = "r2"

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/usr/bin/avl_xtrend_blindscan" "${D}/${bindir}"
}

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI[md5sum] = "537af67e2fb6bac63352ecb39f4b24ed"
SRC_URI[sha256sum] = "d42b97994f0f82b3d1c98378c6e59faf6d04760d83027eab7b089342da6d878b"
