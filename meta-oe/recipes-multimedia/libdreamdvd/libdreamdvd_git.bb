SUMMARY = "libdvdnav wrapper for enigma2 based stbs"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "libdvdnav"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRC_URI = "git://github.com/mirakels/libdreamdvd.git;protocol=https"

SRC_URI_append_sh4 = "\
    file://libdreamdvd-1.0-support_sh4.patch;patch=1 \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

CFLAGS += " -std=gnu11"
