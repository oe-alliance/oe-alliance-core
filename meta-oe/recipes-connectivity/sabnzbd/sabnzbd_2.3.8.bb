MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=6c2cd2089133de5067e13a6d4f75afef"


DEPENDS = "python"
RDEPENDS_${PN} = "\
    python-core python-shell python-compression python-crypt python-ctypes python-sqlite3 python-sabyenc \
    python-cheetah python-misc python-subprocess python-html python-email python-yenc python-multiprocessing \
    "
RRECOMMENDS_${PN} = "par2cmdline unrar"

SRC_URI = "http://github.com/sabnzbd/sabnzbd/archive/2.3.8.tar.gz \
    file://sabnzbd \
    file://sabnzbd.conf \
    file://init-functions \
    "

SRC_URI[md5sum] = "4ea752ddd8aaef0a2c81fdfe5f734e91"
SRC_URI[sha256sum] = "c8c35e6ebddfc95162638e460c431c0a3832fa50342b32036fdd6e4a9836b364"

S = "${WORKDIR}/sabnzbd-${PV}"

INSTALLDIR = "/usr/lib/${PN}"

PACKAGES = "${PN}-doc ${PN}-src ${PN}"

FILES_${PN}-src = "${INSTALLDIR}/*/*.py ${INSTALLDIR}/*/*/*.py"
RDEPENDS_${PN}-src = "python"
FILES_${PN}-doc = "${INSTALLDIR}/*.txt ${INSTALLDIR}/licenses ${INSTALLDIR}/interfaces/*/licenses"
FILES_${PN} = "${INSTALLDIR} /etc/init.d/sabnzbd /etc/init.d/init-functions /etc/enigma2/sabnzbd.conf"

inherit update-rc.d
INITSCRIPT_NAME = "sabnzbd"
INITSCRIPT_PARAMS = "defaults"

do_compile() {
    python -O -m compileall .
}

do_install() {
    install -d ${D}${INSTALLDIR}
    cp -r . ${D}${INSTALLDIR}/
    install -d ${D}/etc/init.d
    install -m 755 ${WORKDIR}/sabnzbd ${D}/etc/init.d/sabnzbd
    install -m 755 ${WORKDIR}/init-functions ${D}/etc/init.d/init-functions
    install -d ${D}/etc/enigma2
    install -m 644 ${WORKDIR}/sabnzbd.conf ${D}/etc/enigma2/sabnzbd.conf
}
