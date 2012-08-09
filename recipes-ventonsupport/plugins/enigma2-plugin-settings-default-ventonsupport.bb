DESCRIPTION = "Venton settings 13 19"
LICENSE = "GPL"
MAINTAINER ?= "Venton Support"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

SRCDATE = "10072012"

SRC_URI[md5sum] = "3079118a9f02e6ff3fa4b25178f32800"
SRC_URI[sha256sum] = "b686d7024e859f6baad4c0e6720bd460f4dae62b0582cad9a60a0f4c448391df"

SRC_URI = "http://code-ini.com/software/channelsettings/enigma2-plugin-settings-default-ini-${SRCDATE}.tar.gz"

PR = "r3"
PN = "enigma2-plugin-settings-default-ventonsupport"
PACKAGES = "${PN}"
PROVIDES="virtual/enigma2-settings"

FILES_${PN} = "/etc/enigma2/*"

S="${WORKDIR}/${PN}"

do_install() {
	install -d ${D}/etc/enigma2

	for f in lamedb bouquets* userbouquet*
	do
		install -m 644 ${f} ${D}/etc/enigma2/${f}
	done
}

