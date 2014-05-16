SUMMARY = "DVD navigation multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;beginline=42;endline=43;md5=3f2e6972567beedaa901b809725027ff \
                    file://src/dvdnav.c;beginline=1;endline=19;md5=8270661e7e05a78e14714e0fb3048b12"

DEPENDS = "libdvdread"
inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "4.2.+git${SRCPV}"
PKGV = "4.2.+git${GITPKGV}"
PR = "r5"

SRC_URI = "git://github.com/microe/libdvdnav.git \
           file://0001-dvdnavmini.pc-link-against-libdvdnavmini.patch"

S = "${WORKDIR}/git"

inherit autotools lib_package binconfig pkgconfig

PACKAGES += " libdvdnavmini"
FILES_${PN} = "${libdir}/libdvdnav.*"
FILES_libdvdnavmini = "${libdir}/libdvdnavmini.*"
