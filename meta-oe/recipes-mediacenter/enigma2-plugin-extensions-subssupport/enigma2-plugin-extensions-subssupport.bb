SUMMARY = "Collection of enigma2 subtitles plugins"
HOMEPAGE = "https://github.com/mx3L/subssupport"
HOMEPAGE:openatv = "https://github.com/oe-alliance/subssupport"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

RDEPENDS:${PN} = "python3-xmlrpc python3-compression python3-codecs python3-difflib python3-rarfile unrar"
RDEPENDS:${PN}:append:openatv = " python3-asyncio python3-beautifulsoup4 python3-requests"

inherit gittag

SRCREV = "${AUTOREV}"
PV = "1.5.8+git"
PKGV = "1.5.8+git${GITPKGV}"
SRC_URI = "git://github.com/oe-mirrors/subssupport;protocol=https;branch=master"

# openATV follows the maintained, tagged fork; the other distros decide for themselves.
PV:openatv = "git"
PKGV:openatv = "V${GITPKGVTAG}"
SRC_URI:openatv = "git://github.com/oe-alliance/subssupport;protocol=https;branch=master"

FILES:${PN} = "${libdir}/enigma2/python/Plugins/Extensions/SubsSupport \
${localstatedir}/lib/subssupport"

inherit autotools-brokensep gettext python3native

do_install:append() {
    install -d ${D}${localstatedir}/lib/subssupport
}
