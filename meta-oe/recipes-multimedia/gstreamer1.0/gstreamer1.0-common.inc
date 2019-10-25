GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "487a3f9a30be15c72a98ca6cd744efb9c3d9a68e"
SRCREV_gst_common = "88e512ca7197a45c4114f7fa993108f23245bf50"
SRCREV_gst_libav = "80c8c05457d1a7ba37a15b7150e6797dd4dac8e1"

SRCREV_gst_plugins_bad = "351a3934ece34b9873e90cf0260325d3feb8bdcf"
SRCREV_gst_plugins_base = "2bc1b3882511edf9126c673d6bb3f6dbce22681b"
SRCREV_gst_plugins_good = "203ad39d534e79ad8233418da42779700dc56e59"
SRCREV_gst_plugins_ugly = "3cd445a490cc36eb8c64000cb384f78f9ab7e7af"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"
