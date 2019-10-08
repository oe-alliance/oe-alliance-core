GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "e17bde51b3bcbebe84b381846607bed0f7b7130a"
SRCREV_gst_common = "88e512ca7197a45c4114f7fa993108f23245bf50"
SRCREV_gst_libav = "40f5953828c4d82b39890ad4da4603693eff8af7"

SRCREV_gst_plugins_bad = "f2c75d548d1ab02e7015adb55cd9db0e6e9282af"
SRCREV_gst_plugins_base = "dd556ccf3d5b05c0e0b03398b62617e311797085"
SRCREV_gst_plugins_good = "46989dca9665f5a1b28a83f68415c91fee7bc051"
SRCREV_gst_plugins_ugly = "3f24460e3760fec8b9c82e16196f346876581372"

SRCREV_ffmpeg = "61853f7503530ae11ed407a80463dc7ac2e7362d"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
