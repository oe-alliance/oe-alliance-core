SUMMARY = "tuxbox libtuxtxt"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
DEPENDS = "libpng freetype ${@base_contains("BRAND_OEM", "fulan", "fulan-dvb-modules" , "", d)}"

inherit gitpkgv

SRC_URI = "git://git.code.sf.net/p/openpli/tuxtxt;protocol=git"

SRC_URI_append_sh4 = " \
    file://tuxtxtlib_sh4_fix.patch;patch=1 \
"

S = "${WORKDIR}/git/libtuxtxt"

PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r2"

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
