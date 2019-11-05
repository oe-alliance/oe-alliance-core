GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "d8b8a132850e82e06bb2570776ad2de154a3905f"
SRCREV_gst_libav = "07f33470b34c6677b614e543a6d8f8cf0a7ce7e3"

SRCREV_gst_plugins_bad = "a7ec30dc94ab392e8df0ac0686fa8efc2f05dd0e"
SRCREV_gst_plugins_base = "c363747251c0071fe65b9413693f42d5875e02bd"
SRCREV_gst_plugins_good = "87457a862d3f9e1468553d1e070e16746949a443"
SRCREV_gst_plugins_ugly = "3cd445a490cc36eb8c64000cb384f78f9ab7e7af"

SRCREV_gst_ffmpeg = "b25fc92ead37b0d087ec5af41d391886b814fef7"

S = "${WORKDIR}/git"
