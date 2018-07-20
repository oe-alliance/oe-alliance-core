DESCRIPTION = "${MACHINE} libraries"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
DEPENDS = "zlib libpng freetype curl libxml2 libjpeg-turbo harfbuzz"
RDEPENDS_${PN} = "libbluray"

SRC_URI = "http://source.mynonpublic.com/zgemma/zgemma-libs-${MACHINE}-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install() {
	install -d ${D}${libdir}/hisilicon
	install -m 0755 ${S}/hisilicon/* ${D}${libdir}/hisilicon
}

FILES_${PN} = "${libdir}/hisilicon/*"

INSANE_SKIP_${PN} += "ldflags already-stripped dev-so"