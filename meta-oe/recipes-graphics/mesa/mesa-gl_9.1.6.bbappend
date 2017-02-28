SRC_URI += "file://01_gbm_egl.patch \
            file://02_gbm_no_undefined.patch"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

PACKAGECONFIG = "dri \
  ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} \
  ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'egl wayland', '', d)} \
"

EGL_PLATFORMS = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)}"

do_configure_prepend() {
	# Don't build or install libEGL.
	sed -e 's,\<main\>,,' -i ${S}/src/egl/Makefile.am
}
