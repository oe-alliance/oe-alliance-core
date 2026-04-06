SUMMARY = "Plugin to handle the MSNWeather Converter and Renderer for displaying MSN-Weather in Skin"
MAINTAINER = "community"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

RDEPENDS:${PN} = "enigma2-plugin-extensions-weatherplugin enigma2-plugin-skincomponents-weathercomponent"

SRC_URI = "git://github.com/oe-mirrors/enigma2-plugin-skincomponents-weathercomponent.git;protocol=https;branch=main"

FILES:${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp -r --no-preserve=ownership ${S}/usr/lib/* ${D}${libdir}/
}
