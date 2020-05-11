GST_VERSION_FULL = "1.17.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "70469b4a4c8095da090cef0b8f7286ec60dd691e"
SRCREV_gst_libav = "0a985ff67764c50280ea1de121e511299d4823f6"

SRCREV_gst_plugins_bad = "79e65951a90eebfa496cae35b76b4b9eaa92931e"
SRCREV_gst_plugins_base = "9280d4b8f512565003bf8e310681394c1611d3c1"
SRCREV_gst_plugins_good = "d8f0deadc3533ad0f5b3aef8c2da5a3192f658b7"
SRCREV_gst_plugins_ugly = "8fe8ed3dd2bdcf22097d9886ad5242258b64ee44"

# ffmpeg versions < 4.2.2
SRCREV_gst_ffmpeg = "bd71f039a349b4a448ae7f0eddb65fd8f923345b"

S = "${WORKDIR}/git"
