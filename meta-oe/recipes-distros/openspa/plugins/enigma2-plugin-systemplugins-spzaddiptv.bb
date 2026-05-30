NAME = "spzAddIPTV"
SUMMARY = "For append IPTV Channels in Channels List"
MAINTAINER = "Spaze Team <xx@xxx.xx>"
SECTION = "base"
PRIORITY = "optional"
HOMEPAGE = "http://openspa.info"
RDEPENDS:${PN} = "librtmp ${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugins-bad-rtmp", "gst-plugins-bad-rtmp", d)}" 

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.2+git"
FOLDER = "spzaddiptv"

S = "${UNPACKDIR}/${P}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/SystemPlugins/${NAME}"

do_install() {
    install -d ${D}${PLUGINPATH}
    cp -r ${S}/${FOLDER}/plugin/* ${D}${PLUGINPATH}
}

do_install:append() {
    find ${D}${PLUGINPATH} -name "*.py" -delete
}

FILES:${PN} = "${PLUGINPATH}"

addtask translate after do_configure before do_compile
