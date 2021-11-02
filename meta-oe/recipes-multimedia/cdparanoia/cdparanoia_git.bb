# Copyright (C) 2005, Advanced Micro Devices, Inc.  All Rights Reserved
SUMMARY = "audio extraction tool for sampling CDs"
HOMEPAGE = "http://xiph.org/paranoia/"
LICENSE = "GPLv2+ & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING-GPL;md5=1ed9d357695b2e3ef099df37fed63d96 \
                    file://COPYING-LGPL;md5=d370feaa1c9edcdbd29ca27ea3d2304d"
SECTION = "multimedia"
SRCREV = "${AUTOREV}"
PV = "10.2+gitr${SRCPV}"

SRC_URI = "git://github.com/oe-mirrors/cdparanoia.git;protocol=https \
     file://fixes10.patch \
     file://Makefile.in.patch \
     file://interface_Makefile.in.patch \
     file://paranoia_Makefile.in.patch \
     file://configure.in.patch"

S = "${WORKDIR}/git"

PARALLEL_MAKE = ""

inherit autotools-brokensep pkgconfig gitpkgv

PACKAGES += "libcdparanoia libcdparanoia-dev libcdparanoia-static"

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
