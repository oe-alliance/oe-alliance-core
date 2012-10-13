DESCRIPTION = "Venton settings 13 19"
LICENSE = "GPL"
MAINTAINER ?= "Venton Support"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

SRCDATE = "18082012"

SRC_URI[md5sum] = "258e073ca4312f5740bf2545236cdb2e"
SRC_URI[sha256sum] = "f48efa771ab2dfd92b5fd6fce9f6bb533cac5ac4f21b93d2e08589b999e9b1d7"

SRC_URI = "http://code-ini.com/software/channelsettings/enigma2-plugin-settings-default-ini-${SRCDATE}.tar.gz"

PR = "r3"
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

