DESCRIPTION = "shared lib for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "curl"

PV = "${SRCDATE}"
PR = "r1"

inherit lib_package

SRC_URI = "http://source.mynonpublic.com/clap/${MACHINE}-hilib-${SRCDATE}.tar.gz"

S = "${WORKDIR}/hilib"

do_configure() {
}

do_compile() {
}

do_install() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/*.so ${D}${libdir}/
}

do_package_qa() {
}

FILES_${PN} += "${libdir}/*"

INSANE_SKIP_${PN} = "already-stripped dev-so"
