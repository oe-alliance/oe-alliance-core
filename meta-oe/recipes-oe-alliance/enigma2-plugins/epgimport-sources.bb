SUMMARY = "enigma2 sources and channels for the EPG importer"
MAINTAINER = "oe-alliance"
Homepage = "https://github.com/oe-alliance/EPGImport-Sources.git"
inherit gittag allarch
require conf/license/license-gplv2.inc

RCONFLICTS:${PN} = "epgimport-rytec"
RREPLACES:${PN} = "epgimport-rytec"
PROVIDES += "epgimport-rytec"
RPROVIDES:${PN} = "epgimport-rytec"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/oe-alliance/EPGImport-Sources.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

FILES:${PN} = "${sysconfdir}/epgimport"

do_install() {
    install -d ${D}${sysconfdir}/epgimport
    install -m 644 ${S}/*.sources.xml ${D}${sysconfdir}/epgimport/
}
