MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=235357e51b50dc9738e5b57e2b49fb6a"

PR = "r1"

DEPENDS = "python"
RDEPENDS_${PN} = "\
	python-core python-shell python-compression python-crypt python-ctypes python-sqlite3 \
	python-cheetah python-misc python-subprocess python-html python-email python-yenc \
	"
RRECOMMENDS_${PN} = "par2cmdline unrar"

# Beta URI
# SRC_URI = "${SOURCEFORGE_MIRROR}/project/sabnzbdplus/sabnzbdplus-beta/${PV}/SABnzbd-${PV}-src.tar.gz \

SRC_URI = "${SOURCEFORGE_MIRROR}/project/sabnzbdplus/sabnzbdplus/${PV}/SABnzbd-${PV}-src.tar.gz \
	file://sabnzbd"

SRC_URI[md5sum] = "b79f214ac491eb859b2c6c9124fc8d1b"
SRC_URI[sha256sum] = "36ddfe48a770381bf8cdd4bd807416a09c7d05c4939c17861977d9413b1e3fa2"

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
