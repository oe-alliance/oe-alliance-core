SUMMARY = "Kodi texture packer"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM ?= "file:///${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

# Keep the native skin packer on the exact Kodi source revision used by
# stb-kodi. Mixing a later TexturePacker HEAD with an older target binary can
# silently produce an incompatible Textures.xbt during otherwise reproducible
# image builds.
SRCREV = "9076c3af25d5e9a74af0506eadfdb761c680a580"
PV = "22.0+gitr"
SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=master"

inherit cmake gettext python3-dir python3native

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}/tools/depends/native/TexturePacker/src"

DEPENDS = " \
    giflib \
    jpeg \
    libpng \
    lzo \
"

OECMAKE_CXX_FLAGS:append = " -DTARGET_POSIX -std=gnu++17 -I${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}/xbmc/linux"

EXTRA_OECMAKE = "-DKODI_SOURCE_DIR=${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/TexturePacker ${D}${bindir}
}

BBCLASSEXTEND = "native"
