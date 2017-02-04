SUMMARY = "tuxbox libtuxtxt"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
DEPENDS = "libpng freetype"
PACKAGE_ARCH := "${MACHINE_ARCH}"

inherit gitpkgv

SRC_URI = "git://github.com/OpenPLi/tuxtxt.git;protocol=git \
    file://tuxtxt_getPressedKey.patch \
    file://acinclude_fix_DVB_API_VERSION_check_for_gcc5.patch \
"

SRC_URI_append_sh4 = " \
    file://tuxtxtlib_sh4_fix.patch;patch=1 \
"

SRC_URI_append_xc7362 = " \
    file://tuxtxt_clear_screen.patch \
"

SRC_URI_append_openatv = " \
    file://libtuxtxt_FHD.patch \
    "

S = "${WORKDIR}/git/libtuxtxt"

PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r8"

EXTRA_OECONF = "--with-boxtype=generic"

inherit autotools pkgconfig

do_configure_prepend() {
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

FILES_${PN} = "/usr/lib/libtuxtxt.so.*"
FILES_${PN}-dev = "/usr/include/ /usr/lib/libtuxtxt.la /usr/lib/libtuxtxt.so /usr/lib/pkgconfig/tuxbox-tuxtxt.pc"
