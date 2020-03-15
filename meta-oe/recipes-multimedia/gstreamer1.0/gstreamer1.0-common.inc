GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "a1aebe73d545cae4d499d12c70fee25f46f7cc90"
SRCREV_gst_libav = "c8b88847919c9cb26205d91111fa11d4d4f44883"

SRCREV_gst_plugins_bad = "c02f6032626aebfa8359cbed60b60ee5324760e9"
SRCREV_gst_plugins_base = "7b00e5de9998aa5b997a288d2d298f852427fe6a"
SRCREV_gst_plugins_good = "66296fcae3a006ea0c97043827db110b7d274307"
SRCREV_gst_plugins_ugly = "9273903286b0fe7a9d8a5279c44a5e149e55ff24"

SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
