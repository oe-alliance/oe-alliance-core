NAME = "openSPAnetTest"
SUMMARY = "NetTest by Ookla"
MAINTAINER = "OpenSPA Team"
SECTION = "extra"
PRIORITY = "optional"
HOMEPAGE = "http://openspa.info"

inherit python3-dir python3native
require assume-gplv2.inc
require spaopenplugins.inc

PV = "2.0+git"
FOLDER = "openSPAnetTest"

S = "${WORKDIR}/${P}"
UNPACKDIR = "${S}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${NAME}"
PYFILES = "${libdir}/enigma2/python/Components"

do_compile() {
    python3 -m compileall -o2 -b ${S}/git/${FOLDER}
}

do_install() {
    find ${S}/git/${FOLDER}/plugin/ -name "*.py" ! -name "speedtest.py" -exec rm -rf {} \;
    find ${S}/git/${FOLDER}/Components/ -name "*.py" -exec rm -rf {} \;
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
