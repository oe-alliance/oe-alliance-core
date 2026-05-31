NAME = "PlutoTV"
SUMMARY = "Play VOD PlutoTV and create bouquet with LiveTV channels"
MAINTAINER = "OpenSPA Team"
SECTION = "extra"
HOMEPAGE = "http://openspa.info"
PRIORITY = "optional"
DEPENDS += "python3"
RDEPENDS:${PN} = "python3-json python3-requests"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.2+git"
FOLDER = "PlutoTV"

S = "${UNPACKDIR}/${P}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${NAME}"

do_install() {
    install -d ${D}${PLUGINPATH}
    cp -r ${S}/${FOLDER}/plugin/* ${D}${PLUGINPATH}
}

do_install:append() {
    find ${D}${PLUGINPATH} -name "*.py" -delete
}

FILES:${PN} = "${PLUGINPATH}"

addtask translate after do_configure before do_compile
