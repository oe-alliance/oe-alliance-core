PR_append = "-vuplus0"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://tinyxml.pc "

do_install_append() {
	mkdir -p ${D}/usr/lib/pkgconfig
	install -m 644 ${WORKDIR}/tinyxml.pc ${D}/usr/lib/pkgconfig
}

FILES_${PN} = "/"

