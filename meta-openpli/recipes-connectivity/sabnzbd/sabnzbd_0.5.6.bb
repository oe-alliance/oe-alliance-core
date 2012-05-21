MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=f2b0e8d69071fdbf437ab1e542ffdf77"

PR = "r1"

DEPENDS = "python"
RDEPENDS_${PN} = "\
	python-core python-shell python-compression python-crypt python-ctypes python-sqlite3 \
	python-cheetah python-misc python-subprocess python-html python-email python-yenc \
	"
RRECOMMENDS_${PN} = "par2cmdline unrar"

SRC_URI = "${SOURCEFORGE_MIRROR}/sabnzbdplus/sabnzbdplus/sabnzbd-${PV}/SABnzbd-${PV}-src.tar.gz \
	file://sabnzbd"

SRC_URI[md5sum] = "6a43179f42e31bed3faa0135a213bc65"
SRC_URI[sha256sum] = "dc83f3e2f9af2d3cd32cd02e88de7522e37b5d0732e59498b67244cca1d5cb22"

S = "${WORKDIR}/SABnzbd-${PV}"

INSTALLDIR = "/usr/lib/${PN}"

PACKAGES = "${PN}-doc ${PN}-src ${PN}"

FILES_${PN}-src = "${INSTALLDIR}/*/*.py ${INSTALLDIR}/*/*/*.py"
FILES_${PN}-doc = "${INSTALLDIR}/*.txt ${INSTALLDIR}/licenses ${INSTALLDIR}/interfaces/*/licenses"
FILES_${PN} = "${INSTALLDIR} /etc/init.d/sabnzbd"

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
}
