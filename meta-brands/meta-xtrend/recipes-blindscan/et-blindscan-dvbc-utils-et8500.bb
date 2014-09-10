SUMMARY = "Utils for DVB-C blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "http://xtrendet.net/${MACHINE}-dvbc-blindscan-${PV}.zip"

PROVIDES += "virtual/blindscan-dvbc"
RPROVIDES_${PN} += "virtual/blindscan-dvbc"

PV = "1.2"
PR = "r1"

S = "${WORKDIR}"

do_compile() {
}

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI[md5sum] = "a36f20b9a372a352fdd7aeadb7716323"
SRC_URI[sha256sum] = "f48eae3e3503c590ba88b62cf90b22f5fede717d08a53e644370b00b7725fda0"
