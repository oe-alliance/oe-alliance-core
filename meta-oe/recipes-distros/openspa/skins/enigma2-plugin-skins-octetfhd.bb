SUMMARY = "Enigma2 Skin OctEtFHD"
MAINTAINER = "norhap"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"

SRC_URI = "git://github.com/openspa/OctEtFHD-skin.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "/usr/share/enigma2 /usr/lib/enigma2/python/Components"

FILES:${PN}-src += "${libdir}/enigma2/python/Components/Converter/RunningEvents.py"

PACKAGES = "${PN}-src ${PN}"

do_install:append() {
        install -d ${D}${libdir}/enigma2/python/Components
        cp -rp ${S}${libdir}/enigma2/python/Components/* ${D}${libdir}/enigma2/python/Components
}

do_package_qa[noexec] = "1"
deltask do_populate_sysroot

do_install() {
	cp -r  --preserve=mode,links ${S}${prefix} ${D}/
}
