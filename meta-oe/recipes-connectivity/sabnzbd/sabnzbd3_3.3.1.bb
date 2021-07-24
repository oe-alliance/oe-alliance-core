SUMMARY = "SABnzbd - The automated Usenet download tool"
DESCRIPTION = "SABnzbd is an Open Source Binary Newsreader written in Python."
HOMEPAGE = "https://sabnzbd.org"
MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=d4bfc20f13bd0e7363c57525136734ee"

DEPENDS = "${PYTHON_PN}"
RDEPENDS_${PN} = "\
    ${PYTHON_PN}-cheetah ${PYTHON_PN}-compression ${PYTHON_PN}-core ${PYTHON_PN}-crypt ${PYTHON_PN}-ctypes ${PYTHON_PN}-email ${PYTHON_PN}-html \
    ${PYTHON_PN}-misc ${PYTHON_PN}-multiprocessing ${PYTHON_PN}-sqlite3 ${PYTHON_PN}-shell ${PYTHON_PN}-sabyenc3 ${PYTHON_PN}-configobj \
    ${PYTHON_PN}-cryptography ${PYTHON_PN}-feedparser ${PYTHON_PN}-cheroot ${PYTHON_PN}-cherrypy ${PYTHON_PN}-portend ${PYTHON_PN}-chardet \
    ${PYTHON_PN}-notify2 ${PYTHON_PN}-puremagic ${PYTHON_PN}-guessit ${PYTHON_PN}-sgmllib3k ${PYTHON_PN}-more-itertools ${PYTHON_PN}-modules \
    "

RRECOMMENDS_${PN} = "par2cmdline unrar p7zip-full"

SRC_URI = "https://github.com/sabnzbd/sabnzbd/releases/download/3.3.1/SABnzbd-3.3.1-src.tar.gz \
    file://sabnzbd \
    file://sabnzbd.conf \
    file://init-functions \
    "

SRC_URI[md5sum] = "62483a336ea4a1241e4e122e67f2f217"
SRC_URI[sha256sum] = "4ca22408e2f4f88880c8d0fb480c231c0b51d144eb871d01870dcb9b14199ec3"

S = "${WORKDIR}/SABnzbd-${PV}"

INSTALLDIR = "${libdir}/${PN}"

PACKAGES = "${PN}-doc ${PN}"

FILES_${PN}-doc = "${INSTALLDIR}/*.txt ${INSTALLDIR}/licenses ${INSTALLDIR}/interfaces/*/licenses"
FILES_${PN} = "${INSTALLDIR} /etc/init.d/sabnzbd /etc/init.d/init-functions /etc/enigma2/sabnzbd.conf"

inherit update-rc.d
INITSCRIPT_NAME = "sabnzbd"
INITSCRIPT_PARAMS = "defaults"

do_install() {
    install -d ${D}${INSTALLDIR}
    cp -r . ${D}${INSTALLDIR}/
    rm -rf ${D}${INSTALLDIR}/.git
    install -d ${D}/etc/init.d
    install -m 755 ${WORKDIR}/sabnzbd ${D}/etc/init.d/sabnzbd
    install -m 755 ${WORKDIR}/init-functions ${D}/etc/init.d/init-functions
    install -d ${D}/etc/enigma2
    install -m 644 ${WORKDIR}/sabnzbd.conf ${D}/etc/enigma2/sabnzbd.conf
}
