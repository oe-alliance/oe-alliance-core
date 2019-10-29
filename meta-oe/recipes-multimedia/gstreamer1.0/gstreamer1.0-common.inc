GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "d25a88e3a15b9f38a66ae6d2735c625b6f8db476"
SRCREV_gst_libav = "07f33470b34c6677b614e543a6d8f8cf0a7ce7e3"

SRCREV_gst_plugins_bad = "d8372736c6ca1c31f3622888eaa506fb01be626e"
SRCREV_gst_plugins_base = "8b92d144a110bc80a9be46b587691123817f63d6"
SRCREV_gst_plugins_good = "ae80fa4731b729d7e546af212d08a93a555ff3c1"
SRCREV_gst_plugins_ugly = "3cd445a490cc36eb8c64000cb384f78f9ab7e7af"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

S = "${WORKDIR}/git"
