SUMMARY = "C++ parsing library for Service Information (SI) in DVB systems"
AUTHOR = "Andreas Oberritter"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.3.8+git${SRCPV}"
PKGV = "0.3.8+git${GITPKGV}"

SRC_URI = "git://git.opendreambox.org/git/obi/libdvbsi++.git \
	file://fix_section_len_check.patch \
	file://ac3.patch \
	"

SRC_URI_append_sh4 = " \
    file://libdvbsi++-fix-unaligned-access-on-SuperH.patch \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
