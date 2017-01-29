DESCRIPTION = "serviceapp service for enigma2"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "enigma2 uchardet openssl"
RDEPENDS_${PN} = "enigma2 uchardet openssl exteplayer3"
RCONFLICTS_${PN} = "enigma2-plugin-extensions-serviceapp"
RREPLACES_${PN} = "enigma2-plugin-extensions-serviceapp"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/mx3L/serviceapp.git;branch=master"

S = "${WORKDIR}/git"

inherit autotools gitpkgv pythonnative pkgconfig

CXXFLAGS += " -std=gnu++98"

PV = "0.5+git${SRCPV}"
PKGV = "${GITPKGVTAG}"

PR = "r2"

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

#do_install_append() {
#	rm ${D}${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/*.pyc
#}

FILES_${PN} = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/*.pyo \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/serviceapp.so"

FILES_${PN}-src = "\
	/usr/lib/enigma2/python/Plugins/SystemPlugins/ServiceApp/*.py \
	/usr/lib/enigma2/python/Plugins/SystemPlugins/ServiceApp/locale/*/LC_MESSAGES/*.mo \
	/usr/src/debug/* \
	"

FILES_${PN}-dbg = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/serviceapp.la"
