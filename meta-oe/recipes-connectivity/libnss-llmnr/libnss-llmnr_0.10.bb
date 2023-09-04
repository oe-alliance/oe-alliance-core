SUMMARY = "Name Service Switch module for Link-local Multicast Name Resolution (LLMNR)"
HOMEPAGE = "https://github.com/jmaggard10/nss-llmnr"
SECTION = "libs"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d5025d4aa3495befef8f17206a5b0a1"

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/jmaggard10/nss-llmnr.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

EXTRA_OECONF = "--libdir=${base_libdir} --disable-lynx"

# suppress warning, but don't bother with autonamer
LEAD_SONAME = "libnss_llmnr.so"
DEBIANNAME:${PN} = "libnss-llmnr"


do_configure:prepend() {
	sed -e 's/SUBDIRS=src doc/SUBDIRS\=src/' -i ${S}/Makefile.am
}

pkg_postinst:${PN} () {
	sed -e '/^hosts:/s/\s*\<llmnr\>//' \
		-e 's/\(^hosts:.*\)\(\<files\>\)\(.*\)\(\<dns\>\)\(.*\)/\1\2 llmnr_minimal [NOTFOUND=return]\3\4 llmnr\5/' \
		-i $D${sysconfdir}/nsswitch.conf
}

pkg_prerm:${PN} () {
	sed -e '/^hosts:/s/\s*\<llmnr\>//' \
		-e '/^hosts:/s/\s*llmnr_minimal\s\+\[NOTFOUND=return\]//' \
		-i $D${sysconfdir}/nsswitch.conf
}
