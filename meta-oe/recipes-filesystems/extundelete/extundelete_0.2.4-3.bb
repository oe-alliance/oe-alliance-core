SUMMARY = "Utility to undelete files from an ext3 or ext4 partition."
DESCRIPTION = "extundelete uses the information stored in the partition’s journal \
to attempt to recover a file that has been deleted. \
There is no guarantee that any particular file will be able to be undeleted."
HOMEPAGE = "https://www.kali.org/tools/extundelete/"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "e2fsprogs"

inherit autotools-brokensep pkgconfig

SRC_URI = "https://source.mynonpublic.com/extundelete-0.2.4-3.zip"

SRC_URI[md5sum] = "4634a64214099404fe41699574bab5d7"
SRC_URI[sha256sum] = "2fc3443f598d45ecda582c84cc1b3e24b9d22d0dcbfae37584d503cb016d436d"

S = "${WORKDIR}"

do_install () {
	install -d ${D}/usr/bin
	install -m 755 ${S}/src/extundelete ${D}/usr/bin
	}
