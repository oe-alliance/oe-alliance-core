SUMMARY = "Rytec's sources and channels for the EPG importer"
MAINTAINER = "oe-alliance"
inherit allarch
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

inherit gittag allarch

SRCREV="${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

SRC_ORIGIN ?= "git://github.com/oe-alliance/EPGImport-Sources.git;protocol=https;branch=main"
SRC_URI := "${SRC_ORIGIN} "

S = "${WORKDIR}/git"

DEPENDS = "enigma2-plugin-extensions-epgimport"
PACKAGES = "${PN}"

FILES_${PN} = "${sysconfdir}/epgimport"

do_install() {
	install -d ${D}${sysconfdir}/epgimport
	install -m 644 ${S}/rytec.sources.xml ${D}${sysconfdir}/epgimport/rytec.sources.xml
	install -m 644 ${S}/russian.sources.xml ${D}/etc/epgimport/russian.sources.xml
	install -m 644 ${S}/turkish.sources.xml ${D}${sysconfdir}/epgimport/turkish.sources.xml
	install -m 644 ${S}/spainKoala.sources.xml ${D}${sysconfdir}/epgimport/spainKoala.sources.xml
	install -m 644 ${S}/polandAzman.sources.xml ${D}${sysconfdir}/epgimport/polandAzman.sources.xml
}
