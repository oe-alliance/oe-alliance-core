# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtlocation-git:"

SRC_URI += " \
	file://Fix-compilation-for-no-opengl-builds.patch \
"