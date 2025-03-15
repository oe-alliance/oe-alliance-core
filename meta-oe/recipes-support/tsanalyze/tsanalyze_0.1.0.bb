SUMMARY = "Transport Stream Analyzer for DVB"
DESCRIPTION = "A simple analyzer for MPEG Transport Streams, useful for debugging DVB streams."
HOMEPAGE = "https://github.com/junka/tsanalyze"
require conf/license/license-gplv2.inc

DEPENDS = "python3-pybind11-native"

SRC_URI = "git://github.com/junka/tsanalyze.git;protocol=https;branch=master"
SRCREV = "eb71e512764080233fa0a4d29f2452ef3733c107"

S = "${WORKDIR}/git"

inherit cmake python3targetconfig python3native

do_install() {
    install -d ${D}${bindir}
    install -m 0755 tsanalyze ${D}${bindir}/
}

FILES:${PN} += "${bindir}/tsanalyze"
