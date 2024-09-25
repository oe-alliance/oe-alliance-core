SUMMARY = "tuxbox tuxtxt for 32bit framebuffer"
DESCRIPTION = "tuxbox tuxtxt for enigma2"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "freetype libtuxtxt"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "2.0+git"
PKGV = "2.0+git${GITPKGV}"

SRC_URI = "git://github.com/oe-alliance/tuxtxt.git;protocol=https;branch=master"

inherit autotools pkgconfig

S = "${WORKDIR}/git/tuxtxt"

EXTRA_OECONF = "--with-boxtype=${MACHINE} --with-configdir=/etc \
    ${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
     "
require conf/python/python3-compileall.inc

PACKAGES = "${PN}-src ${PN}-dbg ${PN}-dev ${PN}"
FILES:${PN}-src = "/usr/src ${libdir}/enigma2/python/Plugins/Extensions/Tuxtxt/*.py"
FILES:${PN} = "${libdir}/libtuxtxt32bpp.so.* /usr/share/fonts ${libdir}/enigma2/python/Plugins/Extensions/Tuxtxt/*.pyc /etc/tuxtxt"
CONFFILES:${PN} = "/etc/tuxtxt/tuxtxt2.conf"
