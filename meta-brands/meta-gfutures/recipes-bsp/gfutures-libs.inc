DESCRIPTION = "${MACHINE} libraries"
SECTION = "base"
PRIORITY = "required"
require conf/license/license-close.inc
DEPENDS = "zlib libpng freetype curl libxml2 libjpeg-turbo harfbuzz"
RDEPENDS:${PN} = "libbluray openssl10"
REV = "r1"
PR = "${SRCDATE}.${REV}"
LIBSVER ?= "${@bb.utils.contains('TARGET_ARCH', 'aarch64', '64' , '', d)}"

SRC_URI = "https://source.mynonpublic.com/gfutures/gfutures-libs${LIBSVER}-${HICHIPSET}-${SRCDATE}.zip"

S = "${UNPACKDIR}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

# Disable anything auto depending
EXCLUDE_FROM_SHLIBS = "1"

do_install() {
	install -d ${D}${libdir}/hisilicon
	install -m 0755 ${S}/hisilicon/* ${D}${libdir}/hisilicon
	install -m 0755 ${S}/ffmpeg/* ${D}${libdir}/hisilicon
	install -d ${D}${base_libdir}
	ln -sf ${base_libdir}/ld-linux-armhf.so.3 ${D}${libdir}/hisilicon/ld-linux.so
	rm -f ${D}${libdir}/hisilicon/libcrypto.so.* ${D}${libdir}/hisilicon/libssl.so.*
}

do_package_qa() {
}

FILES:${PN} = "${libdir}/hisilicon/* ${base_libdir}"

INSANE_SKIP:${PN} += "ldflags already-stripped dev-so"
