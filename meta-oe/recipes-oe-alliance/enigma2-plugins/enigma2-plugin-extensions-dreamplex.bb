SUMMARY = "Plex Client for Enigma2 by Don Davici"
MAINTAINER = "OE-Alliance"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit autotools-brokensep gitpkgv gettext ${PYTHON_PN}targetconfig ${PYTHON_PN}native

SRCREV = "${AUTOREV}"
PV = "2.2.0+git${SRCPV}"
PKGV = "2.2.0+git${GITPKGV}"
PR = "r1"

DEPENDS += "enigma2 ${PYTHON_PN} python3-setuptools-native"
RDEPENDS:${PN} = "gstreamer1.0-plugins-bad-hls curl mjpegtools ${PYTHON_PN}-ctypes libshowiframe0 ${PYTHON_PN}-pyopenssl ${PYTHON_PN}-pillow"

SRC_URI = "git://github.com/oe-alliance/DreamPlex.git;protocol=https;branch=master"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

PACKAGES += "enigma2-plugin-extensions-dreamplex-meta"
FILES:enigma2-plugin-extensions-dreamplex-meta = "${datadir}/meta"

RPROVIDES:${PN}  = "enigma2-plugin-skinpacks-dreamplex-bluemod-fhd enigma2-plugin-skinpacks-dreamplex-bluemod"
RREPLACES:${PN}  = "enigma2-plugin-skinpacks-dreamplex-bluemod-fhd enigma2-plugin-skinpacks-dreamplex-bluemod"
RCONFLICTS:${PN} = "enigma2-plugin-skinpacks-dreamplex-bluemod-fhd enigma2-plugin-skinpacks-dreamplex-bluemod"

S = "${WORKDIR}/git"

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.txt$', 'enigma2-plugin-%s-doc', '%s (documents)', recursive=True, match_path=True, prepend=True)
}
