PR_append = ".1"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

DEPENDS = "glib-2.0 pango atk jpeg libpng docbook-utils-native cairo gdk-pixbuf"

SRC_URI += " \
	file://001_gdk_window_ensure_native_patch.diff;apply=yes;striplevel=1 \
	file://002_remove_legacy_log_patch.diff;apply=yes;striplevel=1 \
	file://003_disable_demo_build_patch.diff;apply=yes;striplevel=1 \
	"

