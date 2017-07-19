PR_append = ".2"
PACKAGE_ARCH := "${MACHINE_ARCH}"

THISDIR := "${@os.path.dirname(d.getVar('FILE', True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

SRC_URI += " \
	file://001_gdk_window_ensure_native_patch.diff;apply=yes;striplevel=1 \
	file://003_disable_demo_build_patch.diff;apply=yes;striplevel=1 \
	"

SRC_URI_append_sf4008 = " file://004_gdk_input_for_hbbtv.patch"
SRC_URI_append_et1x000 = " file://004_gdk_input_for_hbbtv.patch"
SRC_URI_append_et13000 = " file://004_gdk_input_for_hbbtv.patch"
SRC_URI_append_vuuno4k = " file://002_remove_legacy_log_patch.diff;apply=yes;striplevel=1"
SRC_URI_append_vusolo4k = " file://002_remove_legacy_log_patch.diff;apply=yes;striplevel=1"
SRC_URI_append_vuultimo4k = " file://002_remove_legacy_log_patch.diff;apply=yes;striplevel=1"
PACKAGECONFIG = "directfb"
