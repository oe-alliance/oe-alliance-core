DESCRIPTION="screenshot grabber Quad"
MAINTAINER = "Gigablue"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

RCONFLICTS_ = "aio-grab"
RREPLACES = "aio-grab"

DEPENDS = "jpeg libpng zlib"

PV = "1.0"
PKGV = "1.0"
PR = "r0"

SRC_URI = "file://grab"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/grab" "${D}/${bindir}"
}




