DESCRIPTION = "E2-DarkOS is a modern graphic skin by DimitarCC"
MAINTAINER = "DimitarCC"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

require conf/python/python3-compileall.inc
inherit gitpkgv allarch python3native

SRCREV = "${AUTOREV}"
PV = "1.1+git"
PKGV = "1.1+git${GITPKGV}"

RRECOMMENDS:${PN} = "enigma2-boxlogos"

SRC_URI = "git://github.com/DimitarCC/E2-DarkOS-skin.git;protocol=https;branch=main"

do_install() {
	install -d ${D}${prefix}
	cp -r --no-preserve=ownership ${S}${prefix}/* ${D}${prefix}/
}

FILES:${PN} = "${prefix}/"
FILES:${PN}-src = "${prefix}/lib/enigma2/python/Components/Converter/*.py ${prefix}/lib/enigma2/python/Components/Renderer/*.py"
