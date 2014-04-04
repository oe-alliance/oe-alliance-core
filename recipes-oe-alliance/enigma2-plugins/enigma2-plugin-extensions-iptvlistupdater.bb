SUMMARY = "IPTV Bouquet Updater Plugin by Nobody28 & satinfo "
MAINTAINER = "Nobody28 & satinfo"
SECTION = "extra"
PRIORITY = "optional"
RDEPENDS_${PN} = "gst-plugins-bad-rtmp librtmp1 python-textutils udpxy curl"

require conf/license/license-gplv2.inc

inherit gitpkgv autotools pythonnative
SRCREV = "${AUTOREV}"
PV = "1.50.+git${SRCPV}"
PKGV = "1.50.+git${GITPKGV}"
PR = "r17"

RREPLACES_enigma2-plugin-extensions-iptvlistupdater = "enigma2-plugin-extensions-iptv-list-updater"
RCONFLICTS_enigma2-plugin-extensions-iptvlistupdater = "enigma2-plugin-extensions-iptv-list-updater"

SRC_URI="git://github.com/Nobody28/IPTV-List-Updater.git;protocol=git"

