SUMMARY = "A set of tools for CD recording"
HOMEPAGE = "http://www.cdrkit.org"
DEPENDS = "libcap bzip2"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b30d3b2750b668133fc17b401e1b98f8"
PR = "r1"

SRC_URI = " \
	http://cdrkit.org/releases/${P}.tar.gz;name=archive \
	file://xconfig.patch \
"

inherit cmake

SRC_URI[archive.md5sum] = "3c25505d567113c269dc6e71640646d8"
SRC_URI[archive.sha256sum] = "8b6e90b4068cac6f3a75a501d7a85aba6583b2dc34f434e3eb62d29104b107e5"
