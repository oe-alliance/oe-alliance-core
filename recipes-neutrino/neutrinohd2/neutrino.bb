DESCRIPTION = "neutrinoHD2"
MAINTAINER = "mohousch based on open-nhd2-r64.12 from scp"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://AUTHORS;md5=03e8a220d5624784d6f4b1f4e5ded717"

MODULE = "nhd2-exp"
NEUTRINOHDBIN = "neutrino"

SUBURI = "svn/branches;module=nhd2-exp"

SRCREV = "AUTOINC"

URL = "http://www.cuberevo-forum.eu"

require neutrinohd-extra.inc

RDEPENDS_${PN} += " \
	libattr \
	\
	${GST_BASE_RDEPS} \
	${GST_GOOD_RDEPS} \
	${GST_BAD_RDEPS} \
	${GST_UGLY_RDEPS} \
	"
