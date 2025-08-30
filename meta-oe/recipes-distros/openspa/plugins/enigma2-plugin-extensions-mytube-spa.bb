NAME = "MyTube"
SUMMARY = "Play Youtube videos in your STB"
MAINTAINER = "acid-burn@opendreambox.org"
SECTION = "extra"
HOMEPAGE = "http://openspa.info"
PRIORITY = "optional"
DEPENDS += "${PYTHON_PN}"
RDEPENDS:${PN} = "${PYTHON_PN}-google-api-client ${PYTHON_PN}-oauth2client ${PYTHON_PN}-yt-dlp"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.2+git"
FOLDER = "mytube"


S = "${WORKDIR}/${P}"
UNPACKDIR = "${S}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${NAME}"


do_install() {
    install -d ${D}${PLUGINPATH}
    cp -r ${S}/git/${FOLDER}/plugin/* ${D}${PLUGINPATH}
}

FILES:${PN} = "${PLUGINPATH}"

addtask translate after do_configure before do_compile
