MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=6c2cd2089133de5067e13a6d4f75afef"


DEPENDS = "python"
RDEPENDS_${PN} = "\
    python-core python-shell python-compression python-crypt python-ctypes python-sqlite3 python-sabyenc \
    python-cheetah python-misc python-subprocess python-html python-email python-yenc python-multiprocessing \
    "
RRECOMMENDS_${PN} = "par2cmdline unrar"

SRC_URI = "http://github.com/sabnzbd/sabnzbd/archive/2.3.9.tar.gz \
    file://sabnzbd \
    file://sabnzbd.conf \
    file://init-functions \
    "

SRC_URI[md5sum] = "538817726a4024643bbd09e0aadfd01c"
SRC_URI[sha256sum] = "e5071e66e06e9d10f5d04695cb63aba3e77b0c89deb6dd0f80246218d7940b3c"

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
