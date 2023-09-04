SUMMARY = "OAWeather Converter and Renderer to display Weather in Skin."
MAINTAINER = "jbleyel"
SECTION = "extra"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

RDEPENDS:${PN} = "enigma2-tools-weatherinfo"

require conf/python/python3-compileall.inc

inherit gettext gittag

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI="git://github.com/oe-alliance/OAWeather;protocol=https;branch=main"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-po"

FILES:${PN} += "${libdir}/enigma2/python/Components/*/*.pyc ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/*.pyc \
                ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/*.xml ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/*.png \
                ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/*/*/*.mo \
                ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/Images/*.png ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/Icons/*.png"
FILES:${PN}-src += "${libdir}/enigma2/python/Components/*/*.py {libdir}/enigma2/python/Plugins/Extensions/OAWeather/*.py"
#FILES:${PN}-po += "${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/*.po ${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/*.pot"


do_compile() {
    # generate translation .mo files
	mkdir -p ${S}/locale
	for f in $(find ${S}/po -name *.po ); do
		l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*po\///')
		mkdir -p ${S}/locale/${l%}/LC_MESSAGES
		msgfmt -o ${S}/locale/${l%}/LC_MESSAGES/OAWeather.mo ${S}/po/$l.po
	done
}


do_install() {
    install -d ${D}${libdir}/enigma2/python
    cp -rf ${S}/src/* ${D}${libdir}/enigma2/python/

    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/
    cp -rf ${S}/locale/* ${D}${libdir}/enigma2/python/Plugins/Extensions/OAWeather/locale/
}
