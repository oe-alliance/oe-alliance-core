DESCRIPTION = "BouquetCleanup cleans oversize bouquets of services on not configured satellites."
MAINTAINER = "Huevos"

inherit gitpkgv allarch ${PYTHON_PN}native

require conf/license/license-gplv2.inc

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/Huevos/BouquetCleanup.git;protocol=https;branch=master"

FILES:${PN} = "${prefix}/"

S = "${WORKDIR}/git"

do_install:append() {
	install -d ${D}${prefix}
	cp -r ${S}${prefix}/* ${D}${prefix}/
	python3 -m compileall -o2 -b ${D}
}

FILES:${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/BouquetCleanup/*.py"
