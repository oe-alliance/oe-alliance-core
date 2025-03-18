FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PROVIDES += "librtmp librtmp1"
RPROVIDES:${PN} += "librtmp librtmp1"

SRC_URI:append = " \
	file://0001-KSV-patch-2015-12-15.patch \
	file://0002-fix-build-openssl102q.patch \
	file://0003-add-movecast-thx-testi.patch \
	"
