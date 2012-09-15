DESCRIPTION = "Venton picons 13 19"
MAINTAINER ?= "Venton support"
LICENSE = "GPL"
DEPENDS = "pngcrush-native"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "all"

SRCDATE = "10072012"

SRC_URI[md5sum] = "8d48064d8a2e45b5670d6c0948f97706"
SRC_URI[sha256sum] = "2e861c27ccbd14154eedb275490abb6d0b9e889588d5a2cddd9ab7e54912d64b"

SRC_URI = "http://code-ini.com/software/channelsettings/enigma2-plugin-picons-default-ini-${SRCDATE}.tar.gz"

PR = "r11"
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

