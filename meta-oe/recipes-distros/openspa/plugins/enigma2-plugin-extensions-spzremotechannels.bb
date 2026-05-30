NAME = "spzRemoteChannels"
SUMMARY = "Copy remote channels as stream in channel list & download epg"
MAINTAINER = "Spaze Team <xx@xxx.xx>"
SECTION = "base"
PRIORITY = "optional"
HOMEPAGE = "http://openspa.info"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.2+git"
FOLDER = "spzremotechannels"

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
