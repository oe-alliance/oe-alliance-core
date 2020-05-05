DESCRIPTION = "${MACHINE} libraries"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
DEPENDS = "zlib libpng freetype curl libxml2 libjpeg-turbo harfbuzz"
RDEPENDS_${PN} = "libbluray"
PR = "${SRCDATE}"
LIBSVER ?= "${@bb.utils.contains('TARGET_ARCH', 'aarch64', '64' , '', d)}"

SRC_URI = "http://source.mynonpublic.com/gfutures/gfutures-libs${LIBSVER}-${HICHIPSET}-${SRCDATE}.zip"

S = "${WORKDIR}"

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
	install -d ${D}/lib
	if [ "${GLIBCVERSION}" = "2.25" ]; then
		install -m 0755 ${S}/glibc/* ${D}${libdir}/hisilicon
	else
		ln -sf /lib/ld-linux-armhf.so.3 ${D}${libdir}/hisilicon/ld-linux.so
	fi
}

do_package_qa() {
}

FILES_${PN} = "${libdir}/hisilicon/* /lib/*"

INSANE_SKIP_${PN} += "ldflags already-stripped dev-so"
