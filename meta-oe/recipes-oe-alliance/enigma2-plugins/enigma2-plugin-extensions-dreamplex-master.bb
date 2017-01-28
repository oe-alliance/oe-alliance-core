SUMMARY = "Plex Client for Enigma2 by Don Davici"
MAINTAINER = "Don Davici"

require conf/license/license-gplv2.inc

inherit autotools-brokensep gitpkgv pythonnative

SRCREV = "${AUTOREV}"
PV = "2.1.3+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r2"

PACKAGES = "enigma2-plugin-extensions-dreamplex"
PROVIDES = "enigma2-plugin-extensions-dreamplex"

DEPENDS = "python"
RDEPENDS_${PN} = "${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugins-bad-hls", "gst-plugins-bad-fragmented", d)} curl mjpegtools python-ctypes libshowiframe0"

SRC_URI = "git://github.com/DonDavici/DreamPlex.git;protocol=git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

PACKAGES += "enigma2-plugin-extensions-dreamplex-meta"
FILES_enigma2-plugin-extensions-dreamplex-meta = "${datadir}/meta"

S = "${WORKDIR}/git"

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.txt$', 'enigma2-plugin-%s-doc', '%s (documents)', recursive=True, match_path=True, prepend=True)
}
