PR_append = "-vuplus0"

THISDIR := "${@os.path.dirname(d.getVar('FILE', True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

SRC_URI += " file://tinyxml.pc "

do_install_append() {
	mkdir -p ${D}/usr/lib/pkgconfig
	install -m 644 ${WORKDIR}/tinyxml.pc ${D}/usr/lib/pkgconfig
}

FILES_${PN} = "/"

