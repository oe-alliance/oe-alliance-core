DESCRIPTION = "Plugin to handle the MSNWeather Converter and Renderer for displaying MSN-Weather in Skin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "enigma2-plugin-extensions-weatherplugin enigma2-plugin-skincomponents-weathercomponent"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/WeatherComponentHandler.git;protocol=https;branch=main"
