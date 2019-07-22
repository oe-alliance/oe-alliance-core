GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "86b9f78788cd8e55e289cf01c05eeac1b00e8da8"
SRCREV_gst_common = "f32031c0c1eefb227ab5ce77722a23bf1fe59891"
SRCREV_gst_libav = "4d3cd3983aba981431bbd18d4daf50f350847d5c"

SRCREV_gst_plugins_bad = "96004cd75111f742089b5f9554d20d18f9e83444"
SRCREV_gst_plugins_base = "f72e71903a51ec7be20b938a012ee512aa3c6c34"
SRCREV_gst_plugins_good = "5fde140e6e3dea6008a9eb4d9ba0b907ba9c7db3"
SRCREV_gst_plugins_ugly = "f31f085e7bec95422f95056e2391fe1b7c26ee32"

SRCREV_ffmpeg = "6e0cf9a9df4a21df89825427914e38bd4c6216ba"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"

do_configure_prepend() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
}
