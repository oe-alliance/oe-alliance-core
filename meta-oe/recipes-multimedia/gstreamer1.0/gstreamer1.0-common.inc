GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "6babf1f086cce9cc392e2dc8a6cdf252d9b4cc48"
SRCREV_gst_common = "88e512ca7197a45c4114f7fa993108f23245bf50"
SRCREV_gst_libav = "40f5953828c4d82b39890ad4da4603693eff8af7"

SRCREV_gst_plugins_bad = "6f2ab31d10d0d4181026eafced3568fe39452b4c"
SRCREV_gst_plugins_base = "2409f4f3609d2dce3f65d1a8c4be5e470b7cf9d3"
SRCREV_gst_plugins_good = "9d42b2dd5dea81d0b8f46cb363be954aaaa34d4c"
SRCREV_gst_plugins_ugly = "3f24460e3760fec8b9c82e16196f346876581372"

SRCREV_ffmpeg = "61853f7503530ae11ed407a80463dc7ac2e7362d"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
