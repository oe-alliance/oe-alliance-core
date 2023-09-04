DESCRIPTION = "Merge lamedb files from /tmp into /etc/enigma2/lamedb settings file"
MAINTAINER = "Huevos"

inherit gitpkgv allarch ${PYTHON_PN}native

require conf/license/license-gplv2.inc

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/Huevos/LamedbMerger.git;protocol=https;branch=master"

FILES:${PN} = "${prefix}/"

S = "${WORKDIR}/git"

do_install:append() {
	install -d ${D}${prefix}
	cp -r ${S}${prefix}/* ${D}${prefix}/
	python3 -m compileall -o2 -b ${D}
}

FILES:${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/LamedbMerger/*.py"
