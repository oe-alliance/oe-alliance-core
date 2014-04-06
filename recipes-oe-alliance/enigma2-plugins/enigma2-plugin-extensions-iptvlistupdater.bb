SUMMARY = "IPTV Bouquet Updater Plugin by Nobody28 & satinfo "
MAINTAINER = "Nobody28 & satinfo"
SECTION = "extra"
PRIORITY = "optional"
RDEPENDS_${PN} = "gst-plugins-bad-rtmp librtmp1 python-textutils udpxy curl"

require conf/license/license-gplv2.inc

inherit gitpkgv autotools pythonnative
SRCREV = "${AUTOREV}"
PV = "1.51.+git${SRCPV}"
PKGV = "1.51.+git${GITPKGV}"
PR = "r20"

RREPLACES_enigma2-plugin-extensions-iptvlistupdater = "enigma2-plugin-extensions-iptv-list-updater"
RCONFLICTS_enigma2-plugin-extensions-iptvlistupdater = "enigma2-plugin-extensions-iptv-list-updater"

SRC_URI="git://github.com/Nobody28/IPTV-List-Updater.git;protocol=git"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/usr/lib"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater/locale/*/*/*.po"

# python populate_packages_prepend() {
#     enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
#     do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
#     do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
#     do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
#     do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
#     do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
#     do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
# }

