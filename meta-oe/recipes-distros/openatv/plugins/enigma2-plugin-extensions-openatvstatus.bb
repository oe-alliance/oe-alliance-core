SUMMARY = "openATV Status v1.9 - view status of OpenATV-buildserver"
MAINTAINER = "Mr.Servo"
SECTION = "extra"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gettext gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r0"

SRC_URI="git://github.com/openatv/openatvstatus.git;branch=main;protocol=https"

S = "${WORKDIR}/git"

PLUGIN = "${libdir}/enigma2/python/Plugins/Extensions/OpenATVstatus"

FILES:${PN} = "${PLUGIN}"

do_compile() {
    # generate translation .mo files
	mkdir -p ${S}/po
	for f in $(find ${S}/po -name *.po ); do
		l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*po\///')
		mkdir -p ${S}/locale/${l%}/LC_MESSAGES
		msgfmt -o ${S}/locale/${l%}/LC_MESSAGES/OpenATVstatus.mo ${S}/po/$l.po
	done
}

do_install() {
    install -d ${D}${PLUGIN}
    cp -rp ${S}/src/* ${D}${PLUGIN}

    install -d ${D}${PLUGIN}/locale/
    cp -rf ${S}/locale/* ${D}${PLUGIN}/locale/
}

pkg_postrm:${PN}() {
#!/bin/sh
rm -r ${PLUGIN} > /dev/null 2>&1
exit 0
}

do_package_qa[noexec] = "1"
