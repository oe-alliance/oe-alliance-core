DESCRIPTION = "Userspace library to access USB (version 1.0)"
HOMEPAGE = "http://libusb.sf.net"
BUGTRACKER = "http://www.libusb.org/report"
SECTION = "libs"
LICENSE = "GPL"
require conf/license/license-gplv2.inc

PR = "r5"

SRC_URI = "http://archiv.mixos-support.com/2.6.18-wr-libusb-1.0.8.tar.bz2"

SRC_URI[md5sum] = "20dac0c90f631525123c3e8f5ab7e552"
SRC_URI[sha256sum] = "33c95251b8438d48e7f9c80a38559497588538dde6254eb8ac175ffa8319cece"
S = "${WORKDIR}/libusb1-1.0.8"

FILES_${PN} = "/lib/*"

do_install() {
	cp -rp ${S}/lib ${D}/
}
