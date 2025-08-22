NAME = "FootOnSat"
SUMMARY = "For football fans"
MAINTAINER = "OpenSPA Team"
SECTION = "extra"
PRIORITY = "optional"
HOMEPAGE = "http://openspa.info"
RDEPENDS:${PN} = "${PYTHON_PN}-sqlite3 alsa-utils-aplay"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.7+git"
FOLDER = "footonsat"

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

addtask translate after do_configure before do_compile
