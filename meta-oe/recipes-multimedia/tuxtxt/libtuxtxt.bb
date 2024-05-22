SUMMARY = "tuxbox libtuxtxt"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

DEPENDS = "libpng freetype"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "2.0+git"
PKGV = "2.0+git${GITPKGV}"

SRC_URI = "git://github.com/oe-alliance/tuxtxt.git;protocol=https;branch=master"

SRC_URI:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', 'file://libtuxtxt_FHD.patch', '', d)} \
    "

SRC_URI:append:xc7362 = " \
    file://tuxtxt_clear_screen.patch \
"

S = "${WORKDIR}/git/libtuxtxt"

EXTRA_OECONF = "--with-boxtype=generic"

CFLAGS += "-Wno-error=return-mismatch"

inherit autotools pkgconfig

do_configure:prepend() {
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

FILES:${PN} = "${libdir}/libtuxtxt.so.*"
FILES:${PN}-dev = "/usr/include/ ${libdir}/libtuxtxt.la ${libdir}/libtuxtxt.so ${libdir}/pkgconfig/tuxbox-tuxtxt.pc"
