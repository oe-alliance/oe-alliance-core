NAME = "PlutoTV"
SUMMARY = "Play VOD PlutoTV and create bouquet with LiveTV channels"
MAINTAINER = "OpenSPA Team"
SECTION = "extra"
HOMEPAGE = "http://openspa.info"
PRIORITY = "optional"
DEPENDS += "${PYTHON_PN}"
RDEPENDS:${PN} = "${PYTHON_PN}-json ${PYTHON_PN}-requests"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.2+git"
FOLDER = "PlutoTV"

S = "${WORKDIR}/${P}"
UNPACKDIR = "${S}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${NAME}"

do_install() {
    install -d ${D}${PLUGINPATH}
    cp -r ${S}/git/${FOLDER}/plugin/* ${D}${PLUGINPATH}
}

FILES:${PN} = "${PLUGINPATH}"

addtask translate after do_configure before do_compile
