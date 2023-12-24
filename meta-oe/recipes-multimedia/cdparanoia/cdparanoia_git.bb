# Copyright (C) 2005, Advanced Micro Devices, Inc.  All Rights Reserved
SUMMARY = "audio extraction tool for sampling CDs"
HOMEPAGE = "http://xiph.org/paranoia/"
LICENSE = "GPL-2.0-or-later & LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING-GPL;md5=1ed9d357695b2e3ef099df37fed63d96 \
                    file://COPYING-LGPL;md5=d370feaa1c9edcdbd29ca27ea3d2304d"
SECTION = "multimedia"
SRCREV = "${AUTOREV}"
PV = "10.2+gitr"

SRC_URI = "git://github.com/oe-mirrors/cdparanoia.git;protocol=https;branch=master \
     file://fixes10.patch \
     file://Makefile.in.patch \
     file://interface_Makefile.in.patch \
     file://paranoia_Makefile.in.patch \
     file://configure.in.patch"

S = "${WORKDIR}/git"

PARALLEL_MAKE = ""

inherit autotools-brokensep pkgconfig gitpkgv

PACKAGES += "libcdparanoia libcdparanoia-dev libcdparanoia-static"

FILES:${PN} = "${bindir}/*"
FILES:${PN}-dev = ""
FILES:${PN}-static = ""
FILES:libcdparanoia = "${libdir}/lib*${SOLIBS}"
FILES:libcdparanoia-dev = "${includedir} ${libdir}/lib*${SOLIBSDEV} ${libdir}/pkgconfig"
FILES:libcdparanoia-static = "${libdir}/*.a"

do_install() {
    oe_runmake BINDIR="${D}${bindir}" MANDIR="${D}${datadir}/man/" \
           INCLUDEDIR="${D}${includedir}" LIBDIR="${D}${libdir}" \
           PKGCONFIGDIR="${D}${libdir}/pkgconfig" \
           install
}
