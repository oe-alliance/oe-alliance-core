DESCRIPTION = "OE-Alliance remote control and box image files for Enimga2 and OpenWebIF."
MAINTAINER = "OE-Alliance"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

inherit gitpkgv

SRCREV = "${AUTOREV}"

PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"

SRC_URI = "git://github.com/oe-alliance/remotes;protocol=https;branch=master"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${datadir}/enigma2
	install -d ${D}${datadir}/enigma2/rc
	install -m 0644 ${S}/remotes.xml ${D}${datadir}/enigma2
	if [ ${RCNAME} != "N/A" ] ; then
		install -m 0644 ${S}/png/${RCNAME}.png ${D}${datadir}/enigma2/rc
		install -m 0644 ${S}/xml/${RCNAME}.xml ${D}${datadir}/enigma2/rc
	fi
	install -m 0644 ${S}/png/dmm1.png ${D}${datadir}/enigma2/rc
	install -m 0644 ${S}/xml/dmm1.xml ${D}${datadir}/enigma2/rc
}

FILES_${PN} = "${datadir}/enigma2 ${datadir}/enigma2/rc"
