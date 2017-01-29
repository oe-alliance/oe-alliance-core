MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=7c333056bf8b3834c43ccf2505d10690"

PR = "r1"

DEPENDS = "python"
RDEPENDS_${PN} = "\
    python-core python-shell python-compression python-crypt python-ctypes python-sqlite3 \
    python-cheetah python-misc python-subprocess python-html python-email python-yenc \
    "
RRECOMMENDS_${PN} = "par2cmdline unrar"

# Beta URI
# SRC_URI = "${SOURCEFORGE_MIRROR}/project/sabnzbdplus/sabnzbdplus-beta/${PV}/SABnzbd-${PV}-src.tar.gz

SRC_URI = "${SOURCEFORGE_MIRROR}/project/sabnzbdplus/sabnzbdplus/${PV}/SABnzbd-${PV}-src.tar.gz \
    file://sabnzbd \
    file://sabnzbd.conf \
    file://init-functions \
    "

SRC_URI[md5sum] = "632ba65889eb4f76857886add36dfaaf"
SRC_URI[sha256sum] = "9ced2f4b9486674e8b0a920769710b16e7d96a9981d5aa47b6584f2c3a1e0b62"

S = "${WORKDIR}/SABnzbd-${PV}"

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
