SUMMARY = "SABnzbd - The automated Usenet download tool"
DESCRIPTION = "SABnzbd is an Open Source Binary Newsreader written in Python."
HOMEPAGE = "https://sabnzbd.org"
MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=dc94785ad7ae0960293331f807d14628"
require conf/python/python3-compileall.inc


DEPENDS = "python3"
RDEPENDS:${PN} = "\
    python3-cheetah python3-compression python3-core python3-crypt python3-ctypes python3-email python3-html \
    python3-misc python3-multiprocessing python3-sqlite3 python3-shell python3-sabyenc3 python3-configobj \
    python3-cryptography python3-feedparser python3-cheroot python3-cherrypy python3-portend python3-chardet \
    python3-notify2 python3-puremagic python3-guessit python3-sgmllib3k python3-more-itertools python3-modules \
    python3-rebulk python3-babelfish python3-dateutil python3-pysocks python3-jaraco.context python3-setuptools \
    python3-jaraco.functools python3-jaraco.collections python3-jaraco.text python3-jaraco.classes python3-sabctools python3-apprise \
    "

RRECOMMENDS:${PN} = "par2cmdline unrar p7zip-full"

SRC_URI = "https://github.com/sabnzbd/sabnzbd/releases/download/4.4.1/SABnzbd-4.4.1-src.tar.gz \
    file://sabnzbd \
    file://sabnzbd.conf \
    file://init-functions \
    "

SRC_URI[md5sum] = "4e607b5a187d6081037e71cc1cafef34"
SRC_URI[sha256sum] = "a9ebf273d77c6d3cc9a13b1bd1640b903f4891e58aee3ef5a25595db3aa4d7fb"

S = "${WORKDIR}/SABnzbd-${PV}"

INSTALLDIR = "${libdir}/${PN}"

PACKAGES = "${PN}-doc ${PN}-src ${PN}"

FILES:${PN}-src = "${INSTALLDIR}/*/*.py ${INSTALLDIR}/*/*/*.py"
FILES:${PN}-doc = "${INSTALLDIR}/*.txt ${INSTALLDIR}/licenses ${INSTALLDIR}/interfaces/*/licenses"
FILES:${PN} = "${INSTALLDIR} /etc/init.d/sabnzbd /etc/init.d/init-functions /etc/enigma2/sabnzbd.conf"

inherit update-rc.d
INITSCRIPT_NAME = "sabnzbd"
INITSCRIPT_PARAMS = "defaults"

do_install() {
    install -d ${D}${INSTALLDIR}
    cp -r . ${D}${INSTALLDIR}/
    rm -rf ${D}${INSTALLDIR}/.git
    install -d ${D}/etc/init.d
    install -m 755 ${UNPACKDIR}/sabnzbd ${D}/etc/init.d/sabnzbd
    install -m 755 ${UNPACKDIR}/init-functions ${D}/etc/init.d/init-functions
    install -d ${D}/etc/enigma2
    install -m 644 ${UNPACKDIR}/sabnzbd.conf ${D}/etc/enigma2/sabnzbd.conf
}
