DESCRIPTION = "serviceapp service for enigma2"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "enigma2 uchardet"
RDEPENDS_${PN} = "uchardet exteplayer3"


inherit gitpkgv

PV = "0.5+git${SRCPV}"
PKGV = "0.5+git${GITPKGV}"

PR = "r0"

SRCREV = "7c5a259df8457b5c0369787ff04b7a17a714c67e"
SRC_URI = "git://github.com/mx3L/serviceapp.git;branch=master"

S = "${WORKDIR}/git"

inherit autotools pythonnative

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

do_install_append() {
	rm -f ${D}${libdir}/enigma2/python/Plugins/Extensions/ServiceApp/*.pyc
}

FILES_${PN} = "\
	${libdir}/enigma2/python/Plugins/Extensions/ServiceApp/*.pyo \
	${libdir}/enigma2/python/Plugins/Extensions/ServiceApp/serviceapp.so"

FILES_${PN}-dev = "\
	${libdir}/enigma2/python/Plugins/Extensions/ServiceApp/*.py \
	${libdir}/enigma2/python/Plugins/Extensions/ServiceApp/serviceapp.la"

