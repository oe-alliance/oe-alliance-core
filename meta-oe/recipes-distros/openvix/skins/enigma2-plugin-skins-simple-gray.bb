DESCRIPTION = "Simple and clean multi resolution skin SimpleGray by Taapat"
MAINTAINER = "Taapat"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

inherit gitpkgv allarch ${PYTHON_PN}native

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/Huevos/skin-SimpleGray.git;protocol=https;branch=master"

FILES:${PN} = "${prefix}/"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${prefix}
	cp -r ${S}${prefix}/* ${D}${prefix}/
}
