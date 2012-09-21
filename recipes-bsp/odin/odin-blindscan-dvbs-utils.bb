DESCRIPTION = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://odin_blindscan"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

PV = "1.0"
PR = "r0"

S = "${WORKDIR}/"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/odin_blindscan" "${D}/${bindir}"
}
