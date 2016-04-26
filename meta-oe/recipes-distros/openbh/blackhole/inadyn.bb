MAINTAINER = "Narcis Ilisei"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/PD;md5=b3597d12946881e13cb3b548d1173851"

PV = "v1.96"
PR = "r1"

SRC_URI = "file://inadyn.v1.96.zip \
	   file://inadyn_script.sh \
	"

S = "${WORKDIR}/${PN}"

inherit autotools-brokensep
#update-rc.d

INITSCRIPT_NAME = "inadyn"
INITSCRIPT_PARAMS = "defaults 60 "
# CONFFILES_${PN} = "/etc/inadyn.conf"

do_compile() {
	unset CC
	mv makefile makefile.bak
	sed "s/gcc/\$\(CC\)/" makefile.bak > makefile
	make CC="${CC}" -f makefile
}

do_install() {
	install -d ${D}/usr/bin
	install -m 755 ${S}/bin/linux/inadyn ${D}/usr/bin
	install -d ${D}/etc/init.d
	install -m 755 ${WORKDIR}/inadyn_script.sh ${D}/etc/init.d/inadyn
}
