GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "c740c104047a94b6b9ebd8f698a8976ba62ac0dc"
SRCREV_gst_libav = "e727ce548c28e5a0577148069a7a81f67b9ba630"

SRCREV_gst_plugins_bad = "02c8e66ff1d3e5f72436222459b4aecc439fbe73"
SRCREV_gst_plugins_base = "add3023f09eb70cdf68130dacf4995e219f74bb9"
SRCREV_gst_plugins_good = "619457ae2635eec4a01b8fcc900a84f5a5ea8ff2"
SRCREV_gst_plugins_ugly = "8fe8ed3dd2bdcf22097d9886ad5242258b64ee44"

# ffmpeg versions < 4.2.2
SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
