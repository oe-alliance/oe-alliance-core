require conf/license/license-gplv2.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://update-rc.d.pl \
          "

inherit allarch

DEPENDS_${PN} += "perl insserv"
DEPENDS_${PN}_class-native += "perl insserv"
RDEPENDS_${PN} += "perl insserv"

do_patch() {
}

do_compile() {
}

do_install_append_class-native() {
	# The initscripts recipe requires the stupid yocto link-rc.d to build
	mv ${D}${sbindir}/update-rc.d ${D}${sbindir}/link-rc.d
	install -m 0755 ${WORKDIR}/update-rc.d.pl ${D}${sbindir}/update-rc.d
}

do_install_append_class-target() {
	install -m 0755 ${WORKDIR}/update-rc.d.pl ${D}${sbindir}/update-rc.d
}

BBCLASSEXTEND = "native"
