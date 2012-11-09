DESCRIPTION = "Venton settings 13 19"
LICENSE = "GPL"
MAINTAINER ?= "Venton Support"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

SRCDATE = "09112012"

SRC_URI[md5sum] = "2c5de8b2629b288199758a3e045127e9"
SRC_URI[sha256sum] = "57bca2da39ea3dc4435f1f13d4851f84b3ecb1ef925c34dccb6a7e575bed6f43"

SRC_URI = "http://code-ini.com/software/channelsettings/enigma2-plugin-settings-default-ini-${SRCDATE}.tar.gz"

PR = "r4"
PN = "enigma2-plugin-settings-default-ventonsupport"
PACKAGES = "${PN}"
PROVIDES="virtual/enigma2-settings"

FILES_${PN} = "/etc/enigma2/*"

S="${WORKDIR}/enigma2-plugin-settings-default-ini"

do_install() {
	install -d ${D}/etc/enigma2

	for f in lamedb bouquets* userbouquet*
	do
		install -m 644 ${f} ${D}/etc/enigma2/${f}
	done
}

