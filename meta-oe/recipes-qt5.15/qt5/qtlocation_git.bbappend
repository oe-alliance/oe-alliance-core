# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

INSANE_SKIP:${PN} += "file-rdeps"

FILESEXTRAPATHS:prepend := "${THISDIR}/qtlocation-git:"

SRC_URI += " \
	file://Fix-compilation-for-no-opengl-builds.patch \
"