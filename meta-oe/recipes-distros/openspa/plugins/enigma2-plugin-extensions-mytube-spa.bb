NAME = "MyTube"
SUMMARY = "Play Youtube videos in your STB"
MAINTAINER = "acid-burn@opendreambox.org"
SECTION = "extra"
HOMEPAGE = "http://openspa.info"
PRIORITY = "optional"
DEPENDS += "python3"
RDEPENDS:${PN} = "python3-google-api-python-client python3-google-auth python3-oauth2client python3-yt-dlp"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.2+git"
FOLDER = "mytube"

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
