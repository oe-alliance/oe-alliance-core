FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://disable-srgb-profile-checks.patch \
    "

PR = "r1"
