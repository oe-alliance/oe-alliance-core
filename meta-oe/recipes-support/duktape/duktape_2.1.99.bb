DESCRIPTION = "Duktape is an embeddable Javascript engine, with a focus on portability and compact footprint."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=fddbe4ed8ac933555f193488d973da85"

SRC_URI = "file://duktape-2.1.99.tar.xz"

do_compile() {
	oe_runmake -f Makefile.cmdline
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/duk ${D}${bindir}/
}

FILES_${PN} = "${bindir}"
