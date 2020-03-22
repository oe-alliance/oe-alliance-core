GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "99901ffab4dbb96f6994c9927b6c0f63df373812"
SRCREV_gst_libav = "c8b88847919c9cb26205d91111fa11d4d4f44883"

SRCREV_gst_plugins_bad = "2e5fee17a3567cc5841b02fe3d8d92334ac97099"
SRCREV_gst_plugins_base = "82113702813d551b000d6ba4527e7e115af88c1e"
SRCREV_gst_plugins_good = "818dde2e025dd21cb5152f4e0756ff577ee49a92"
SRCREV_gst_plugins_ugly = "9b1e183256ec17d641a22d9308bf91cb24199914"

SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
