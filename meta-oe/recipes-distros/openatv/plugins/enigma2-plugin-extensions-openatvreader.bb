SUMMARY = "openATV Reader for forum opena.tv"
MAINTAINER = "Mr.Servo"
SECTION = "extra"

RDEPENDS:${PN} = "python3-beautifulsoup4"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI="git://github.com/openatv/openatvreader.git;branch=master;protocol=https"

S = "${WORKDIR}/git"

PLUGIN = "${libdir}/enigma2/python/Plugins/Extensions/OpenATVreader"

FILES:${PN} = "${PLUGIN}"

do_install() {
    install -d ${D}${PLUGIN}
    cp -rp ${S}/src/* ${D}${PLUGIN}
}

pkg_postrm:${PN}() {
#!/bin/sh
rm -r ${PLUGIN} > /dev/null 2>&1
exit 0
}

do_package_qa[noexec] = "1"
