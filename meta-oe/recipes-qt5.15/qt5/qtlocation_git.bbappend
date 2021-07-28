# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

INSANE_SKIP_${PN} += "file-rdeps"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtlocation-git:"

SRC_URI += " \
	file://Fix-compilation-for-no-opengl-builds.patch \
"