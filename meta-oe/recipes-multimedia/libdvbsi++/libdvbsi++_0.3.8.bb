SUMMARY = "C++ parsing library for Service Information (SI) in DVB systems"
AUTHOR = "Andreas Oberritter"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343"

SRCREV = "ff57e585c47fd62b484d0a8f96fe4f020f5840e3"

SRC_URI = "git://git.opendreambox.org/git/obi/libdvbsi++.git \
	file://fix_section_len_check.patch \
	file://nit.patch \
	"
PR = "r2"

SRC_URI_append_sh4 = " \
    file://libdvbsi++-fix-unaligned-access-on-SuperH.patch \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
