HOMEPAGE = "http://www.alumnit.ca/wiki/?WvDial"
DESCRIPTION = "WvDial is a program that makes it easy to connect your Linux workstation to the Internet."

require conf/license/license-gplv2.inc

PR = "r5"

LICENSE = "GPL"

SRC_URI[default.md5sum] = "8c8580213f1ec5075d59b927ff4491cb"
SRC_URI[default.sha256sum] = "f65a3ee4f6cdc2785a146f99db8a8b9ecfdf07385c95dca59a90efcba342f5ab"
SRC_URI[legacy.md5sum] = "b06ea43d4b6978bfa990ee5310b748d4"
SRC_URI[legacy.sha256sum] = "3e22f9a7d12b1aaafe2668d53a1566f0217db751bf5dad9f92730301c8085109"

SRC_URI = "http://code-ini.com/software/tools/wvdial-1.61.tar.gz;name=default"
SRC_URI_ebox5000 = "http://archiv.mixos-support.com/2.6.18-wr-wvdial-1.61.tar.gz;name=legacy"
SRC_URI_ebox5100 = "http://archiv.mixos-support.com/2.6.18-wr-wvdial-1.61.tar.gz;name=legacy"
SRC_URI_dm800 = "http://archiv.mixos-support.com/2.6.18-wr-wvdial-1.61.tar.gz;name=legacy"

DEPENDS = "wvstreams"
RDEPENDS = "ppp"

FILES_${PN} = "/usr/*"
FILES_${PN} += "/etc/*"

do_install() {
	cp -rp ${S}/usr ${D}/
	cp -rp ${S}/etc ${D}/

}