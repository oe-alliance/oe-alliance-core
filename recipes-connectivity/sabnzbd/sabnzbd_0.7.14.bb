MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=de848e27f035c0367b77e02c8139ee95"

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

SRC_URI[md5sum] = "69421a6d05fde2f69bf69f8b7c1c97e4"
SRC_URI[sha256sum] = "b5aefb2911349697db3be86eca815622a2bddb4ab370ed5522b4b0322d45c843"

S = "${WORKDIR}/SABnzbd-${PV}"

INSTALLDIR = "/usr/lib/${PN}"

PACKAGES = "${PN}-doc ${PN}-src ${PN}"

FILES_${PN}-src = "${INSTALLDIR}/*/*.py ${INSTALLDIR}/*/*/*.py"
FILES_${PN}-doc = "${INSTALLDIR}/*.txt ${INSTALLDIR}/licenses ${INSTALLDIR}/interfaces/*/licenses"
FILES_${PN} = "${INSTALLDIR} /etc/init.d/sabnzbd /etc/init.d/init-functions /etc/enigma2/sabnzbd.conf"

inherit update-rc.d
INITSCRIPT_NAME = "sabnzbd"
INITSCRIPT_PARAMS = "defaults"

do_compile() {
    python -m compileall .
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
