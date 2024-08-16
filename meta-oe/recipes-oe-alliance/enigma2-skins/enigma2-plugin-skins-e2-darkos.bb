DESCRIPTION = "E2-DarkOS is a modern graphic skin by DimitarCC"
MAINTAINER = "DimitarCC"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

inherit gitpkgv allarch ${PYTHON_PN}native

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

RRECOMMENDS:${PN} = "enigma2-boxlogos"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/DimitarCC/E2-DarkOS-skin.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${prefix}
	cp -r ${S}${prefix}/* ${D}${prefix}/
	python3 -m compileall -o2 -b ${D}
}

FILES:${PN} = "${prefix}/"
FILES:${PN}-src = "${prefix}/lib/enigma2/python/Components/Converter/*.py ${prefix}/lib/enigma2/python/Components/Renderer/*.py"