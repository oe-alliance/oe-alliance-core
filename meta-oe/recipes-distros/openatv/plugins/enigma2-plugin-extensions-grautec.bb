SUMMARY = "drivers for grautec oleed display for dreambox"
MAINTAINER = "grautec"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI="git://github.com/openatv/grautec.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* ${sysconfdir}"
FILES_${PN}-dbg = "${sysconfdir}/grautec/*/.debug/*.ko"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rc3.d
	install -d ${D}${sysconfdir}/rc6.d
    chmod -R 777 ${S}/dreambox
    cp -rp ${S}/dreambox/* ${D}/
	ln -sf ../init.d/usbtftdisplay.sh ${D}${sysconfdir}/rc3.d/S90usbtftdisplay
	ln -sf ../init.d/killusbtftdisplay.sh ${D}${sysconfdir}/rc6.d/K50killusbtftdisplay
}

do_package_qa() {
}