DESCRIPTION = "serviceapp service for enigma2"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "enigma2"

SRCREV = "6be7b41e2f6bbb700d3b0d85c365016b22eaf7d5"
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
	rm -f ${D}/usr/lib/enigma2/python/Plugins/Extensions/ServiceApp/*.pyc
}

FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/ServiceApp/"
FILES_${PN}-dev = "${libdir}/enigma2/python/Plugins/Extensions/ServiceApp/serviceapp.la"
