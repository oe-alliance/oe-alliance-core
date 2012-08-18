DESCRIPTION = "Venton settings 13 19"
LICENSE = "GPL"
MAINTAINER ?= "Venton Support"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

SRCDATE = "18082012"

SRC_URI[md5sum] = "7663896022389da931dd2d43abc6a725"
SRC_URI[sha256sum] = "d685c82f2a1b0a6e37f1e1b7b3b8da7720238a716349e00fe99aa1bab45a642d"

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

