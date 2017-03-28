SUMMARY = "Collection of enigma2 subtitles plugins"
HOMEPAGE = "https://github.com/mx3L/subssupport"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
PR = "r0"

RDEPENDS_${PN} = "python-xmlrpc python-compression python-codecs python-zlib python-difflib unrar"

SRCREV = "7a9a809bd5938472181a69c8786842df50d689fa"
SRC_URI = "git://github.com/mx3L/subssupport;protocol=git;branch=master"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/SubsSupport \
${localstatedir}/lib/subssupport"

inherit autotools-brokensep gettext

do_install_append() {
    install -d ${D}${localstatedir}/lib/subssupport
}
