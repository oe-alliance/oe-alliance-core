FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "2"

SRC_URI_append_et5x00 = " file://disable_early_fb.patch"
