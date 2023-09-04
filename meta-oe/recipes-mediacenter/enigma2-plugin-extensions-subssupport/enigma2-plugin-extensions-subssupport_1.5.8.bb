SUMMARY = "Collection of enigma2 subtitles plugins"
HOMEPAGE = "https://github.com/mx3L/subssupport"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

RDEPENDS:${PN} = "${PYTHON_PN}-xmlrpc ${PYTHON_PN}-compression ${PYTHON_PN}-codecs ${PYTHON_PN}-difflib ${PYTHON_PN}-rarfile unrar"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.5.8+git"
PKGV = "1.5.8+git${GITPKGV}"
VER = "1.5.8"
SRC_URI = "git://github.com/oe-mirrors/subssupport;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir}/enigma2/python/Plugins/Extensions/SubsSupport \
${localstatedir}/lib/subssupport"

inherit autotools-brokensep gettext ${PYTHON_PN}native

do_install:append() {
    install -d ${D}${localstatedir}/lib/subssupport
}
