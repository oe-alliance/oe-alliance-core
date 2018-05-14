SUMMARY = "tuxbox tuxtxt for 32bit framebuffer"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
DEPENDS = "freetype libtuxtxt"
SUMMARY = "tuxbox tuxtxt for enigma2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv

SRC_URI = "git://github.com/OpenPLi/tuxtxt.git;protocol=git \
           file://0001-Workaround-for-Gigablue-Quad-receivers.patch \
           file://0002-Use-separate-transparency-for-menu-and-teletext.patch \
           file://0003-Write-enigma2-settings-to-tuxtxt2-conf.patch \
           file://acinclude_fix_DVB_API_VERSION_check_for_gcc5.patch \
           ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', ' \
           file://tuxtxt.ttf \
           file://tuxtxt_nonbold.ttf \
           ', '', d)} \
"

SRC_URI_append_AML8726 = " file://0001-add-HBGIC-for-wetek.patch"
SRC_URI_append_AMLS905 = " file://0001-add-HBGIC-for-wetek.patch"
SRC_URI_append_AML905D = " file://0001-add-HBGIC-for-wetek.patch"

SRC_URI_append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', 'file://tuxtxt_FHD.patch', '', d)} \
    "

S = "${WORKDIR}/git/tuxtxt"

PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r18"

do_configure_prepend() {
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
    if ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', 'true', 'false', d)}; then
        sed 's/UseTTF 0/UseTTF 1/g' -i ${S}/data/tuxtxt2.conf
        sed 's/TTFWidthFactor16 28/TTFWidthFactor16 29/g' -i ${S}/data/tuxtxt2.conf
        sed 's/TTFHeightFactor16 16/TTFHeightFactor16 14/g' -i ${S}/data/tuxtxt2.conf
        sed 's/TTFShiftY 0/TTFShiftY 2/g' -i ${S}/data/tuxtxt2.conf
    fi
}

do_configure_prepend_openvix () {
    sed 's/UseTTF 0/UseTTF 1/g' -i ${S}/data/tuxtxt2.conf
    sed 's/TTFWidthFactor16 28/TTFWidthFactor16 26/g' -i ${S}/data/tuxtxt2.conf
    sed 's/TTFHeightFactor16 16/TTFHeightFactor16 14/g' -i ${S}/data/tuxtxt2.conf
    sed 's/TTFShiftY 0/TTFShiftY 2/g' -i ${S}/data/tuxtxt2.conf
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'tuxtxtfhd', 'true', 'false', d)}; then
        install -d ${D}/usr/share/fonts
        rm -f ${D}/usr/share/fonts/tuxtxt.ttf
        rm -f ${D}/usr/share/fonts/tuxtxt_nonbold.ttf
        cp -f ${WORKDIR}/tuxtxt.ttf ${D}/usr/share/fonts/tuxtxt.ttf
        cp -f ${WORKDIR}/tuxtxt_nonbold.ttf ${D}/usr/share/fonts/tuxtxt_nonbold.ttf
    fi
}

PACKAGES = "${PN}-src ${PN}-dbg ${PN}-dev ${PN}"
FILES_${PN}-src = "/usr/src ${libdir}/enigma2/python/Plugins/Extensions/Tuxtxt/*.py"
FILES_${PN} = "${libdir}/libtuxtxt32bpp.so.* /usr/share/fonts ${libdir}/enigma2/python/Plugins/Extensions/Tuxtxt/*.pyo /etc/tuxtxt"
CONFFILES_${PN} = "/etc/tuxtxt/tuxtxt2.conf"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-boxtype=generic --with-configdir=/etc \
    ${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
     "
