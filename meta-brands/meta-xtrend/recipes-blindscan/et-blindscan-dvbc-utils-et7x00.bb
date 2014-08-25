SUMMARY = "Utils for DVB-C blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "http://xtrendet.net/${MACHINE}-dvbc-blindscan-${PV}.zip"

PROVIDES += "virtual/blindscan-dvbc"
RPROVIDES_${PN} += "virtual/blindscan-dvbc"

PV = "1.4"
PR = "r2"

S = "${WORKDIR}"

do_compile() {
}

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI[md5sum] = "3fff0dc4669b58f2db6017df1f1e5368"
SRC_URI[sha256sum] = "83eb9cb8ce5fbc3411d7ee6c0a8c52ed7a9f2ef19881dd788e83a5c21494556b"
