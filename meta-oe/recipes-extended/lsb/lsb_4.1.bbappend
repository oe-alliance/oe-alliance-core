FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-lsb_release-to-work-with-busybox-head-and-find.patch"
