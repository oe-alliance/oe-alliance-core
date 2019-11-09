FILESEXTRAPATHS_prepend_class-target := "${THISDIR}/qtbase-git:"

SRC_URI_append_class-target = "\
	file://0001-add-eglfs_libvupl-to-src-plugins-platforms-eglfs-dev-5.9.patch \
	file://0002-add-configure_json-5.9.patch \
	file://0003-add-src-plugins-platforms-eglfs-deviceintegration-eg-5.9.patch \
	file://0004-add-debug-to-src-plugins-platforms-eglfs-api-qeglfsi-5.9.patch \
"

DEPENDS_append_class-target = " libvupl libgles"
RDEPENDS_${PN}_append_class-target = " libvupl libgles"

PACKAGECONFIG_append_class-target = " accessibility examples"
PACKAGECONFIG_remove_class-target = "gl"
