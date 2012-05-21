SUMMARY = "tuxbox tuxtxt for 32bit framebuffer"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
DEPENDS = "freetype libtuxtxt"
DESCRIPTION = "tuxbox tuxtxt for enigma2"

inherit gitpkgv

SRC_URI = "git://openpli.git.sourceforge.net/gitroot/openpli/tuxtxt;protocol=git"

S = "${WORKDIR}/git/tuxtxt"

PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r0"

PACKAGES = "${PN}-src ${PN}-dbg ${PN}"
FILES_${PN}-dbg = "/usr/lib/.debug"
FILES_${PN}-src = "/usr/src /usr/lib/enigma2/python/Plugins/Extensions/Tuxtxt/*.py"
FILES_${PN} = "/usr/lib/libtuxtxt32bpp.so.* /usr/share/fonts /usr/lib/enigma2/python/Plugins/Extensions/Tuxtxt/*.pyo /etc/tuxtxt"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-boxtype=generic --with-configdir=/etc \
	${@base_contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
	"
