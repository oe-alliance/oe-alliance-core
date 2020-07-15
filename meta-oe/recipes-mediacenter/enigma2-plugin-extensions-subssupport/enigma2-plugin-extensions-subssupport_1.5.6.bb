SUMMARY = "Collection of enigma2 subtitles plugins"
HOMEPAGE = "https://github.com/mx3L/subssupport"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

RDEPENDS_${PN} = "${PYTHON_PN}-xmlrpc ${PYTHON_PN}-compression ${PYTHON_PN}-codecs ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-zlib", "", d)} ${PYTHON_PN}-difflib unrar"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.5.6+git${SRCPV}"
PKGV = "1.5.6+git${GITPKGV}"
VER = "1.5.6"
SRC_URI = "git://github.com/oe-mirrors/subssupport;protocol=git;branch=master"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/SubsSupport \
${localstatedir}/lib/subssupport"

inherit autotools-brokensep gettext ${PYTHON_PN}native

do_install_append() {
    install -d ${D}${localstatedir}/lib/subssupport
}
