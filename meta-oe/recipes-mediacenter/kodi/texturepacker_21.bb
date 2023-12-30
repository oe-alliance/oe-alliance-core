SUMMARY = "Kodi texture packer"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM ?= "file:///${WORKDIR}/git/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

SRCREV = "${AUTOREV}"
PV = "21.0+gitr"
SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=master"

inherit cmake gettext python3-dir python3native

S = "${WORKDIR}/git/tools/depends/native/TexturePacker/src"

DEPENDS = " \
    giflib \
    jpeg \
    libpng \
    lzo \
"

OECMAKE_CXX_FLAGS:append = " -DTARGET_POSIX -std=gnu++17 -I${WORKDIR}/git/xbmc/linux"

EXTRA_OECMAKE = "-DKODI_SOURCE_DIR=${WORKDIR}/git"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/TexturePacker ${D}${bindir}
}

BBCLASSEXTEND = "native"
