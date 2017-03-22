DESCRIPTION = "libgles v3ddriver headers"
LICENSE = "CLOSED"

PR = "r0"

SRC_URI = "file://skylake-v3ddriver-headers.tar.gz"

S = "${WORKDIR}"

do_compile() {
}

do_install_append() {
	install -d ${D}/${includedir}
	install -m 0644 ${S}/v3dplatform.h ${D}${includedir}/
	for d in EGL GLES GLES2 GLES3 KHR; do
		install -d ${D}${includedir}/$d
		for f in ${S}/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d/
		done
	done
}

FILES_${PN}-dev = "/usr/include/*"