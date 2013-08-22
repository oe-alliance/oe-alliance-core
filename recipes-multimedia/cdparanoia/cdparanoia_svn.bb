# Copyright (C) 2005, Advanced Micro Devices, Inc.  All Rights Reserved
SUMMARY = "audio extraction tool for sampling CDs"
HOMEPAGE = "http://xiph.org/paranoia/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING-GPL;md5=1ed9d357695b2e3ef099df37fed63d96 \
                    file://COPYING-LGPL;md5=d370feaa1c9edcdbd29ca27ea3d2304d"
SECTION = "multimedia"
SRCREV = "17289"
PV = "10.2+svnr${SRCPV}"
PR = "r1"

SRC_URI = "svn://svn.xiph.org/trunk;module=cdparanoia;proto=http \
	 file://fixes10.patch \
	 file://Makefile.in.patch \
	 file://interface_Makefile.in.patch \
	 file://paranoia_Makefile.in.patch \
	 file://configure.in.patch"

S = "${WORKDIR}/cdparanoia"

PARALLEL_MAKE = ""

inherit autotools pkgconfig

PACKAGES += "libcdparanoia libcdparanoia-dev libcdparanoia-static"

LICENSE_libcdparanoia = "LGPLv2.1"
LICENSE_libcdparanoia-dev = "LGPLv2.1"
LICENSE_libcdparanoia-static = "LGPLv2.1"

FILES_${PN} = "${bindir}/*"
FILES_${PN}-dev = ""
FILES_${PN}-static = ""
FILES_libcdparanoia = "${libdir}/lib*${SOLIBS}"
FILES_libcdparanoia-dev = "${includedir} ${libdir}/lib*${SOLIBSDEV} ${libdir}/pkgconfig"
FILES_libcdparanoia-static = "${libdir}/*.a"

do_install() {
	oe_runmake BINDIR="${D}${bindir}" MANDIR="${D}${datadir}/man/" \
		   INCLUDEDIR="${D}${includedir}" LIBDIR="${D}${libdir}" \
		   PKGCONFIGDIR="${D}${libdir}/pkgconfig" \
		   install
}
