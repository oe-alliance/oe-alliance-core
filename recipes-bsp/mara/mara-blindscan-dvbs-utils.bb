DESCRIPTION = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://mara_blindscan"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

PV = "1.0"
PR = "r1"

S = "${WORKDIR}/"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/mara_blindscan" "${D}/${bindir}"
}
