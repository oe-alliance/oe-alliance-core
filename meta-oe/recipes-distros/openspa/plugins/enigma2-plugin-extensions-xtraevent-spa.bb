NAME = "xtraEvent"
SUMMARY = "XtraEvent"
MAINTAINER = "OpenSPA Team"
SECTION = "extra"
PRIORITY = "optional"
HOMEPAGE = "http://openspa.info"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "4.9+git"
FOLDER = "xtraevents"

S = "${WORKDIR}/${P}"
UNPACKDIR = "${S}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${NAME}"
PYFILES = "${libdir}/enigma2/python/Components"

do_install() {
    install -d ${D}${PLUGINPATH}
    install -d ${D}${PYFILES}

    cp -r ${S}/git/${FOLDER}/plugin/* ${D}${PLUGINPATH}
    cp -r ${S}/git/${FOLDER}/Components/* ${D}${PYFILES}
}

FILES:${PN} = "${PLUGINPATH} ${PYFILES}"


