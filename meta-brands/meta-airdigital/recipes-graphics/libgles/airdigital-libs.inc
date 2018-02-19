DESCRIPTION = "${MACHINE} libraries"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
DEPENDS = "zlib libpng freetype curl libxml2 libjpeg-turbo harfbuzz"
RDEPENDS_${PN} = "libbluray"

SRC_URI = "http://source.mynonpublic.com/zgemma/${MACHINE}-libs-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}${libdir}/hisilicon
	install -m 0755 ${S}/*.so ${D}${libdir}/hisilicon
}

do_package_qa() {
}

FILES_${PN} = "${libdir}/hisilicon/*"

INSANE_SKIP_${PN} += "ldflags already-stripped dev-so"