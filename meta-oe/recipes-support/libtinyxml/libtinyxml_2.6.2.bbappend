PR:append = "-vuplus0"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://tinyxml.pc "

do_install:append() {
	mkdir -p ${D}/usr/lib/pkgconfig
	install -m 644 ${UNPACKDIR}/tinyxml.pc ${D}/usr/lib/pkgconfig
}

FILES:${PN} = "/"

