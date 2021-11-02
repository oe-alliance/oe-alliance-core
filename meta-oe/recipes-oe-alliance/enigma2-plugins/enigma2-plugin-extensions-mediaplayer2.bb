SUMMARY = "MediaPlayer 2 for Enigma2"
HOMEPAGE = "https://github.com/mx3L/mediaplayer2"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "CLOSED"

RDEPENDS_${PN} = "python-xmlrpc python-compression python-codecs python-zlib python-difflib unrar enigma2-plugin-extensions-subssupport"

SRCREV = "${AUTOREV}"
inherit gitpkgv
PV = "0.74+git${SRCPV}"
PKGV = "0.74+git${GITPKGV}"

SRC_URI = "git://github.com/mx3L/mediaplayer2;protocol=https;branch=master"

S = "${WORKDIR}/git"

do_install_append () {
    mkdir -p ${D}/${libdir}/enigma2/python/Plugins/Extensions/mediaplayer2
    cp -r ${S}/plugin/* ${D}/${libdir}/enigma2/python/Plugins/Extensions/mediaplayer2/
}

FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/mediaplayer2"

inherit autotools-brokensep 
