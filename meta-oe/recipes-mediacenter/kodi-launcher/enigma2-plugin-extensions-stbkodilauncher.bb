SUMMARY = "Minimal Enigma2 launcher for standalone STB Kodi"
DESCRIPTION = "Adds Kodi entries to the Enigma2 plugin and extensions menus and exits Enigma2 through the existing QUIT_KODI session path."

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} += "virtual-kodi kodi-addons-meta"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/STBKodiLauncher.git;protocol=https;branch=main"

RPROVIDES:${PN} = "enigma2-plugin-extensions-kodi"
RREPLACES:${PN} = "enigma2-plugin-extensions-kodi"
RCONFLICTS:${PN} = "enigma2-plugin-extensions-kodi"
