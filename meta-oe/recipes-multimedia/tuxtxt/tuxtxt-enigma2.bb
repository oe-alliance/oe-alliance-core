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

SRC_URI = "git://github.com/oe-alliance/tuxtxt.git;protocol=https;branch=master \
           ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', ' \
           file://tuxtxt.ttf \
           file://tuxtxt_nonbold.ttf \
           ', '', d)} \
"

SRC_URI:append:AML8726 = " file://0001-add-HBGIC-for-wetek.patch"
SRC_URI:append:AMLS905 = " file://0001-add-HBGIC-for-wetek.patch"
SRC_URI:append:AML905D = " file://0001-add-HBGIC-for-wetek.patch"

SRC_URI:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', 'file://tuxtxt_FHD.patch', '', d)} \
    "

inherit autotools pkgconfig

S = "${WORKDIR}/git/tuxtxt"

EXTRA_OECONF = "--with-boxtype=generic --with-configdir=/etc \
    ${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
     "

do_configure:prepend() {
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
    if ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', 'true', 'false', d)}; then
        sed 's/UseTTF 0/UseTTF 1/g' -i ${S}/data/tuxtxt2.conf
        sed 's/TTFWidthFactor16 28/TTFWidthFactor16 29/g' -i ${S}/data/tuxtxt2.conf
        sed 's/TTFHeightFactor16 16/TTFHeightFactor16 14/g' -i ${S}/data/tuxtxt2.conf
    fi
}

do_configure:prepend:openvix () {
    sed 's/UseTTF 0/UseTTF 1/g' -i ${S}/data/tuxtxt2.conf
    sed 's/TTFWidthFactor16 28/TTFWidthFactor16 26/g' -i ${S}/data/tuxtxt2.conf
    sed 's/TTFHeightFactor16 16/TTFHeightFactor16 14/g' -i ${S}/data/tuxtxt2.conf
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', 'true', 'false', d)}; then
        install -d ${D}/usr/share/fonts
        rm -f ${D}/usr/share/fonts/tuxtxt.ttf
        rm -f ${D}/usr/share/fonts/tuxtxt_nonbold.ttf
        cp -f ${WORKDIR}/tuxtxt.ttf ${D}/usr/share/fonts/tuxtxt.ttf
        cp -f ${WORKDIR}/tuxtxt_nonbold.ttf ${D}/usr/share/fonts/tuxtxt_nonbold.ttf
    fi
}

require conf/python/python3-compileall.inc

PACKAGES = "${PN}-src ${PN}-dbg ${PN}-dev ${PN}"
FILES:${PN}-src = "/usr/src ${libdir}/enigma2/python/Plugins/Extensions/Tuxtxt/*.py"
FILES:${PN} = "${libdir}/libtuxtxt32bpp.so.* /usr/share/fonts ${libdir}/enigma2/python/Plugins/Extensions/Tuxtxt/*.pyc /etc/tuxtxt"
CONFFILES:${PN} = "/etc/tuxtxt/tuxtxt2.conf"
