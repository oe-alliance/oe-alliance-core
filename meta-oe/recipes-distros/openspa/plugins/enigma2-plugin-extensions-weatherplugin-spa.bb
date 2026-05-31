NAME = "WeatherPlugin"
SUMMARY = "WeatherPlugin by Dr.Best modified by OpenSPA"
MAINTAINER = "OpenSPA Team"
SECTION = "extra"
PRIORITY = "optional"
HOMEPAGE = "http://openspa.info"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://weatherplugin/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

RDEPENDS:${PN} = "python3-twisted-web"

require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.0+git"

FOLDER = "weatherplugin"

S = "${UNPACKDIR}/${P}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${NAME}"
PYFILES = "/usr"

do_install() {
    install -d ${D}${PLUGINPATH}
    install -d ${D}${PYFILES}

    cp -r ${S}/${FOLDER}/plugin/* ${D}${PLUGINPATH}
    cp -r ${S}/${FOLDER}/usr/* ${D}${PYFILES}
}

do_install:append() {
    find ${D}${PLUGINPATH} -name "*.py" -delete
    find ${D}${PYFILES} -name "*.py" -delete
}

FILES:${PN} = "${PLUGINPATH} ${PYFILES}"

addtask translate after do_configure before do_compile
