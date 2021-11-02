SUMMARY = "Name Service Switch module for Link-local Multicast Name Resolution (LLMNR)"
HOMEPAGE = "https://github.com/jmaggard10/nss-llmnr"
SECTION = "libs"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d5025d4aa3495befef8f17206a5b0a1"

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/jmaggard10/nss-llmnr.git;protocol=https"

S = "${WORKDIR}/git"

EXTRA_OECONF = "--libdir=${base_libdir} --disable-lynx"

# suppress warning, but don't bother with autonamer
LEAD_SONAME = "libnss_llmnr.so"
DEBIANNAME_${PN} = "libnss-llmnr"


do_configure_prepend() {
	sed -e 's/SUBDIRS=src doc/SUBDIRS\=src/' -i ${S}/Makefile.am
}

pkg_postinst_${PN} () {
	sed -e '/^hosts:/s/\s*\<llmnr\>//' \
		-e 's/\(^hosts:.*\)\(\<files\>\)\(.*\)\(\<dns\>\)\(.*\)/\1\2 llmnr_minimal [NOTFOUND=return]\3\4 llmnr\5/' \
		-i $D${sysconfdir}/nsswitch.conf
}

pkg_prerm_${PN} () {
	sed -e '/^hosts:/s/\s*\<llmnr\>//' \
		-e '/^hosts:/s/\s*llmnr_minimal\s\+\[NOTFOUND=return\]//' \
		-i $D${sysconfdir}/nsswitch.conf
}
