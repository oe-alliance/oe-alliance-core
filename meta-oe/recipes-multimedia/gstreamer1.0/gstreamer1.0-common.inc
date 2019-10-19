GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "35db4a2433fbdf8612cf98b5aab5b14aeb5372f8"
SRCREV_gst_common = "88e512ca7197a45c4114f7fa993108f23245bf50"
SRCREV_gst_libav = "ca478e7546c059574e0f134125deb38c8cbc9111"

SRCREV_gst_plugins_bad = "97a4d0cd7de8cfbf983acc7e37ba2f8fb73c3e19"
SRCREV_gst_plugins_base = "30672ba7d134553e59935ddc875104adba26f25c"
SRCREV_gst_plugins_good = "8e1c224fbc217d43f5b34cf63b3883c871dcecf2"
SRCREV_gst_plugins_ugly = "3cd445a490cc36eb8c64000cb384f78f9ab7e7af"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

SRC_URI_append = " git://gitlab.freedesktop.org/gstreamer/common;protocol=https;branch=master;destsuffix=git/common;name=gst_common"

S = "${WORKDIR}/git"
