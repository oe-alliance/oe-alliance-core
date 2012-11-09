DESCRIPTION = "Venton picons 13 19"
MAINTAINER ?= "Venton support"
LICENSE = "GPL"
DEPENDS = "pngcrush-native"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "all"

SRCDATE = "09112012"

SRC_URI[md5sum] = "e791bb9cacfe5d5078ff138fe797ca2f"
SRC_URI[sha256sum] = "61de31d5d8eb9ea99ceb1a26245f972a8d03582e7ecbbc199c454306a8e7cadf"

SRC_URI = "http://code-ini.com/software/channelsettings/enigma2-plugin-picons-default-ini-${SRCDATE}.tar.gz"

PR = "r12"
PN = "enigma2-plugin-picons-default-ventonsupport"
PACKAGES = "${PN}"

PROVIDES="virtual/enigma2-settings"

FILES_${PN} = "/picon/*"

S="${WORKDIR}/picon"

do_install() {
	install -d ${D}/picon

	for f in *.png
	do
		install -m 644 ${f} ${D}/picon/${f}
	done
}

