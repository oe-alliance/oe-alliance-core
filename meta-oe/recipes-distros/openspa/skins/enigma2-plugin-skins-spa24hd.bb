SUMMARY = "Spa24HD by OpenSPA"
MAINTAINER = "OpenSPA"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r1"

SRC_URI = "git://github.com/OpenSPA/OpenSPA_skins.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2 /usr/lib/enigma2/python/Components"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
   find ${S}/Components/ -name "*.py" -exec rm -rf {} \;
   install -d ${D}/usr/share/enigma2
   cp -rp ${S}/Spa24HD ${D}/usr/share/enigma2/
   chmod -R a+rX ${D}/usr/share/enigma2/
   install -d ${D}/usr/lib/enigma2/python/Components
   cp -rp ${S}/Components/* ${D}/usr/lib/enigma2/python/Components
}

do_package_qa[noexec] = "1"
