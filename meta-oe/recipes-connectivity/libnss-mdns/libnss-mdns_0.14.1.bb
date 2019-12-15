SUMMARY = "Name Service Switch module for Multicast DNS (zeroconf) name resolution"
HOMEPAGE = "https://github.com/lathiat/"
SECTION = "libs"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d5025d4aa3495befef8f17206a5b0a1"

DEPENDS = "avahi"
PR = "r0"

SRC_URI = " \
    https://github.com/lathiat/nss-mdns/releases/download/v${PV}/nss-mdns-${PV}.tar.gz \
    file://0001-remove-unittest.patch \
"

SRC_URI[md5sum] = "39b7f6ccfa0605321c7ee6e78478b83b"
SRC_URI[sha256sum] = "a2094101b735cade45048764ea594bdae2bfa9399837f3c852a5b264416e9c8c"

S = "${WORKDIR}/nss-mdns-${PV}"

localstatedir = "/"

inherit autotools

# EXTRA_OECONF = " --disable-tests"

# suppress warning, but don't bother with autonamer
LEAD_SONAME = "libnss_mdns.so"
DEBIANNAME_${PN} = "libnss-mdns"

RDEPENDS_${PN} = "avahi-daemon"

pkg_postinst_${PN} () {
	sed '
		/^hosts:/ !b
		/\<mdns\(4\|6\)\?\(_minimal\)\?\>/ b
		s/\([[:blank:]]\+\)dns\>/\1mdns4_minimal [NOTFOUND=return] dns/g
		' -i $D${sysconfdir}/nsswitch.conf
}

pkg_prerm_${PN} () {
	sed '
		/^hosts:/ !b
		s/[[:blank:]]\+mdns\(4\|6\)\?\(_minimal\( \[NOTFOUND=return\]\)\?\)\?//g
		' -i $D${sysconfdir}/nsswitch.conf
}
