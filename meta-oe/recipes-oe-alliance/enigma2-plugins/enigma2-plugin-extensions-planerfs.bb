DESCRIPTION = "PlanerFS"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-icalendar"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/PlanerFS.git;protocol=https;branch=main"

do_install:append() {
    install -d ${D}/etc/ConfFS
    install -m 0644 ${S}/PlanerFS/PlanerFS.conf ${D}/etc/ConfFS/PlanerFS.conf
    install -m 0644 ${S}/PlanerFS/sample.ics ${D}/etc/ConfFS/PlanerFS.ics
    install -m 0644 ${S}/PlanerFS/PlanerFS.vcf ${D}/etc/ConfFS/PlanerFS.vcf
}

CONFFILES = "/etc/ConfFS/*"
