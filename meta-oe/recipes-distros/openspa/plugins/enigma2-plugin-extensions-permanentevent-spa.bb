NAME = "PermanentEvent"
SUMMARY = "Permanent Event on screen"
MAINTAINER = "OpenSPA Team"
SECTION = "extra"
PRIORITY = "optional"
HOMEPAGE = "http://openspa.info"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "2.0+git"
FOLDER = "PermanentEvent"

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

do_install:append() {
    find ${D}${PLUGINPATH} -name "*.py" -delete
    find ${D}${PYFILES} -name "*.py" -delete
}

FILES:${PN} = "${PLUGINPATH} ${PYFILES}"

addtask translate after do_configure before do_compile
