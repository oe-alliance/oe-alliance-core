SUMMARY = "tuxbox tuxtxt for 32bit framebuffer"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
DEPENDS = "freetype libtuxtxt"
SUMMARY = "tuxbox tuxtxt for enigma2"

inherit gitpkgv

SRC_URI = "git://git.code.sf.net/p/openpli/tuxtxt;protocol=git \
           file://0001-Workaround-for-Gigablue-Quad-receivers.patch"

S = "${WORKDIR}/git/tuxtxt"

PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r8"

do_configure_prepend_openatv () {
	sed 's/UseTTF 0/UseTTF 1/g' -i ${S}/data/tuxtxt2.conf
	sed 's/TTFWidthFactor16 28/TTFWidthFactor16 26/g' -i ${S}/data/tuxtxt2.conf
	sed 's/TTFHeightFactor16 16/TTFHeightFactor16 14/g' -i ${S}/data/tuxtxt2.conf
	sed 's/TTFShiftY 0/TTFShiftY 2/g' -i ${S}/data/tuxtxt2.conf
}

do_configure_prepend_openvix () {
	sed 's/UseTTF 0/UseTTF 1/g' -i ${S}/data/tuxtxt2.conf
	sed 's/TTFWidthFactor16 28/TTFWidthFactor16 26/g' -i ${S}/data/tuxtxt2.conf
	sed 's/TTFHeightFactor16 16/TTFHeightFactor16 14/g' -i ${S}/data/tuxtxt2.conf
	sed 's/TTFShiftY 0/TTFShiftY 2/g' -i ${S}/data/tuxtxt2.conf
}

PACKAGES = "${PN}-src ${PN}-dbg ${PN}-dev ${PN}"
FILES_${PN}-src = "/usr/src /usr/lib/enigma2/python/Plugins/Extensions/Tuxtxt/*.py"
FILES_${PN} = "/usr/lib/libtuxtxt32bpp.so.* /usr/share/fonts /usr/lib/enigma2/python/Plugins/Extensions/Tuxtxt/*.pyo /etc/tuxtxt"
CONFFILES_${PN} = "/etc/tuxtxt/tuxtxt2.conf"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-boxtype=generic --with-configdir=/etc"
