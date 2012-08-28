HOMEPAGE = "http://www.alumnit.ca/wiki/?WvDial"
DESCRIPTION = "WvDial is a program that makes it easy to connect your Linux workstation to the Internet."

require conf/license/license-gplv2.inc

PR = "r3"

LICENSE = "GPL"

SRC_URI[md5sum] = "8c8580213f1ec5075d59b927ff4491cb"
SRC_URI[sha256sum] = "f65a3ee4f6cdc2785a146f99db8a8b9ecfdf07385c95dca59a90efcba342f5ab"

SRC_URI = "\
	http://code-ini.com/software/tools/wvdial-1.61.tar.gz"

DEPENDS = "wvstreams"
RDEPENDS = "ppp"

FILES_${PN} = "/usr/*"
FILES_${PN} += "/etc/*"

do_install() {
	cp -rp ${S}/usr ${D}/
	cp -rp ${S}/etc ${D}/

}