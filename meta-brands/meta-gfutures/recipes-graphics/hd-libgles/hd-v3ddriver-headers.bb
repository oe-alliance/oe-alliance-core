DESCRIPTION = "libgles v3ddriver headers"
LICENSE = "CLOSED"

PR = "r0"

SRC_URI = "http://downloads.mutant-digital.net/v3ddriver/hd-v3ddriver-headers.tar.gz"

SRC_URI[md5sum] = "15fb88e9cc986d318c893a6b35e88140"
SRC_URI[sha256sum] = "cbefb5746c0a7f9fe9ace0179d61e6a347487395c9785bdc948f2432915c36cf"

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