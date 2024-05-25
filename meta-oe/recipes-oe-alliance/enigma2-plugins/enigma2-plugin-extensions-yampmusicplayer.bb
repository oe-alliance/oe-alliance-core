DESCRIPTION = "Music Player with Artist-Art Background"
HOMEPAGE = "https://github.com/oe-mirrors/yampmusicplayer"
MAINTAINER = "oe-a"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "${PYTHON_PN}-sqlite3 ${PYTHON_PN}-tinytag ${PYTHON_PN}-beautifulsoup4"

inherit gitpkgv gettext setuptools3-openplugins

SRCREV = "${AUTOREV}"
PV = "3.3.1+git"
PKGV = "3.3.1+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/yampmusicplayer.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/YampMusicPlayer"
do_install() {
    install -d ${D}${PLUGINPATH}
    cp -r ${S}/plugin/* ${D}${PLUGINPATH}
    chmod a+rX ${D}${PLUGINPATH}
    install -d ${D}${sysconfdir}/enigma2
    echo 1234567890abcdef7890123456789012 > ${D}${sysconfdir}/enigma2/YampFanarttvPersonalApi.key
}

FILES:${PN} = "${sysconfdir} ${libdir}"

CONFFILES:${PN} = "${sysconfdir}/enigma2/YampFanarttvPersonalApi.key"
