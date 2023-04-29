SUMMARY = "Netsurf Browser"
DESCRIPTION = "Is a free Framebuffer Internet Browser"
MAINTAINER = "TitanNit Team"
SECTION = "network"

LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools pkgconfig

PV = "3.10"

SRC_URI = " http://download.netsurf-browser.org/netsurf/releases/source-full/netsurf-all-${PV}.tar.gz"

SRC_URI += " \
    file://0001-netsurf-32bpp-xbgr8888.patch \
    file://0002-netsurf-event.patch \
    file://0003-netsurf-framebuffer.patch \
    file://0004-netsurf-gui.patch \
    file://0005-netsurf-linux.patch \
    file://0006-netsurf-osk.patch \
    file://0007-netsurf-text.patch \
    file://0008-avoid-system-perl-dependencies.patch \
    file://0009-fix-compilation-without-curl.patch \
    file://0010-framebuffer-Fix-internal-font-generated-source-for-GCC-10.patch \
    file://netsurf.makefile.gcc11.patch \
"

SRC_URI[md5sum] = "f5b2ea34c85775941ab3c2144783d396"
SRC_URI[sha256sum] = "495adf6b6614ce36fca6c605f7c321f9cb4a3df838043158122678ce2b3325b7"

DEPENDS = "libpng curl duktape expat freetype jpeg libpcap openssl virtual/libiconv bison-native gperf-native"

S = "${WORKDIR}/netsurf-all-${PV}"

CFLAGS:append = " \
	-I${STAGING_DIR_TARGET}/usr \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/libpng16 \
	-I${WORKDIR}/netsurf-all-${PV} \
	-I${WORKDIR}/netsurf-all-${PV}/libcss/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libdom/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libhubbub/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libnsbmp/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libnsfb/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libnsgif/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libnslog/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libnspsl/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libnsutils/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libparserutils/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libpencil/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/librosprite/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/librufl/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libsvgtiny/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libutf8proc/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/libwapcaplet/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/netsurf/include/ \
	-I${WORKDIR}/netsurf-all-${PV}/nsgenbind/include/ \
	"

#LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libnsfb/build-x86_64-linux-gnu-arm-oe-linux-gnueabi-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libcss/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libdom/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libhubbub/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libnsbmp/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libnsfb/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libnsgif/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libnslog/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libnspsl/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libnsutils/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libparserutils/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libpencil/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/librosprite/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/librufl/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libsvgtiny/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libutf8proc/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/libwapcaplet/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/netsurf/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "
LDFLAGS:prepend = " -L${WORKDIR}/netsurf-all-${PV}/nsgenbind/build-${BUILD_SYS}-gnu-${TARGET_SYS}-release-lib-static "


do_compile() {
	cd ${WORKDIR}/netsurf-all-${PV}

	if [ ! -e ${WORKDIR}/netsurf-all-${PV}/dom ];then
		ln -s libdom dom
	fi

	NETSURF_ENV="\
	NETSURF_USE_DUKTAPE=NO \
	NETSURF_USE_LIBICONV_PLUG=YES \
	NETSURF_FB_FONTLIB=freetype \
	NETSURF_FB_FRONTEND=linux \
	NETSURF_FB_FONTPATH=/var/usr/share/fonts \
	NETSURF_FB_FONT_SANS_SERIF=FreeSans.ttf \
	NETSURF_FB_FONT_SANS_SERIF_BOLD=FreeSans.ttf \
	NETSURF_FB_FONT_SANS_SERIF_ITALIC=FreeSans.ttf \
	NETSURF_FB_FONT_SANS_SERIF_ITALIC_BOLD=FreeSans.ttf \
	NETSURF_FB_FONT_SERIF=DejaVuSerif.ttf \
	NETSURF_FB_FONT_SERIF_BOLD=DejaVuSerif-Bold.ttf \
	NETSURF_FB_FONT_MONOSPACE=DejaVuSansMono.ttf \
	NETSURF_FB_FONT_MONOSPACE_BOLD=ubuntumono-b-webfont.ttf \
	NETSURF_FB_FONT_CURSIVE=Comic_Sans_MS.ttf \
	NETSURF_FB_FONT_FANTASY=Impact.ttf \
	TARGET=framebuffer \
	PREFIX=/usr"

	make ${NETSURF_ENV}
}

FILES:${PN} = " \
	/usr/bin \
    /usr/share/netsurf \
"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/netsurf-all-${PV}/netsurf/nsfb ${D}/usr/bin/nsfb

    install -d ${D}/usr/share
    cp -rL ${WORKDIR}/netsurf-all-${PV}/netsurf/frontends/framebuffer/res  ${D}/usr/share/netsurf

}

#do_install[vardepsexclude] += "DATETIME"
