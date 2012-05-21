MAINTAINER = "Narcis Ilisei"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=3c34afdc3adf82d2448f12715a255122"

SRCDATE = "20100519"
PV = "v.02.18.24+cvs${SRCDATE}"
PR = "r3"

SRC_URI = "cvs://anonymous@inadyn-mt.cvs.sourceforge.net/cvsroot/inadyn-mt;module=${PN};tag=unicows;date=${SRCDATE} \
	file://inadyn-mt.sh \
	file://inadyn.conf \
	file://remove_host_include_paths.patch \
	"

S = "${WORKDIR}/${PN}"

inherit autotools update-rc.d

INITSCRIPT_NAME = "inadyn-mt"
CONFFILES_${PN} = "/etc/inadyn.conf"

do_compile() {
	make -f makefile-deprecated
}

do_install() {
	install -d ${D}/usr/bin
	install -m 755 ${S}/bin/linux/inadyn-mt ${D}/usr/bin
	install -d ${D}/etc
	install -m 644 ${WORKDIR}/inadyn.conf ${D}/etc/
	install -d ${D}/etc/init.d
	install -m 755 ${WORKDIR}/inadyn-mt.sh ${D}/etc/init.d/inadyn-mt
}
