DESCRIPTION = "GLES/EGL headers"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r0"

SRC_URI = " \
	file://eglheaders.zip \
	"

SRC_URI += "${@base_contains('DISTRO_FEATURES', 'x11', '', 'file://no_x11.patch', d)}"

S = "${WORKDIR}"

do_configure () {
}

do_compile () {
}

do_install_append() {
	for d in EGL ETC1 GLES GLES2 KHR; do
		install -d ${D}${includedir}/$d
		for f in ${S}/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d/
		done
	done
}
