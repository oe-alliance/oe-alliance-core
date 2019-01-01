SUMMARY = "Wireless Display Software For Linux OS (WDS)"
DESCRIPTION = "WDS is a set of libraries for developers who want to build Wi-Fi Display applications on linux"
MAINTAINER = "https://github.com/01org/wds"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=cb8aedd3bced19bd8026d96a8b6876d7"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/01org/wds.git;protocol=http"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

DEPENDS = "gstreamer1.0 bison-native"
RDEPENDS_${PN} = "wpa-supplicant connman"

do_install_append() {
	install -d ${D}${bindir}
	install -m 0755 ${B}/desktop_source/desktop-source-test ${D}${bindir}
	install -m 0755 ${B}/libwds/rtsp/tests/test-wds ${D}${bindir}
	install -m 0755 ${B}/p2p/register-peer-service ${D}${bindir}
	install -m 0755 ${B}/p2p/test-ie ${D}${bindir}
	install -m 0755 ${B}/sink/sink-test ${D}${bindir}
	install -m 0755 ${B}/mirac_network/network-test ${D}${bindir}
	install -m 0755 ${B}/mirac_network/gst-test ${D}${bindir}
}

inherit cmake lib_package

EXTRA_OECMAKE_append = " -DCMAKE_SKIP_RPATH=ON"
