SUMMARY = "OAWeather Converter and Renderer to display Weather in Skin."
MAINTAINER = "jbleyel"
SECTION = "extra"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

RDEPENDS:${PN} = "enigma2-tools-weatherinfo"

require conf/python/python3-compileall.inc

inherit gettext gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRC_URI="git://github.com/oe-alliance/OAWeather;protocol=https;branch=main"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-po"

FILES:${PN} += "${libdir}/enigma2/python/Components/*/*.pyc ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/*.pyc \
                ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/*.xml ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/*.png \
                ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/*.mo"
FILES:${PN}-src += "${libdir}/enigma2/python/Components/*/*.py {libdir}/enigma2/python/Plugins/Extensions/OAWeather/*.py"
FILES:${PN}-po += "${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/*.po ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/*.pot"

do_compile() {
    # generate translation .mo files
    find ${S}/po -name \*.po -execdir sh -c 'msgfmt "$0" -o `basename $0 .po`.mo' '{}' \;
}

do_install() {
    install -d ${D}${libdir}/enigma2/python
    cp -rf ${S}/src/* ${D}${libdir}/enigma2/python/

    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/
    cp -rf ${S}/po/* ${D}${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/
}
