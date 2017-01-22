THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

DEPENDS = "glib-2.0 pango atk jpeg libpng cairo gdk-pixbuf"

SRC_URI += " \
    file://001_gdk_window_ensure_native_patch.diff;apply=yes;striplevel=1 \
    file://003_disable_demo_build_patch.diff;apply=yes;striplevel=1 \
    file://004_gdk_input_for_hbbtv.patch \
    "
