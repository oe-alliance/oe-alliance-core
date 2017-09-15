DESCRIPTION = "libgles v3ddriver headers"
LICENSE = "CLOSED"

PR = "r0"

SRC_URI = "http://source.mynonpublic.com/octagon/nextv-v3ddriver-headers.tar.gz"

SRC_URI[md5sum] = "7d4fac1d19d5b96e8ecb77663b91faab"
SRC_URI[sha256sum] = "46b5f4d7a19024f1f757a9291395d043fbe618c8fcf98c48760a4d77caaa6432"

S = "${WORKDIR}"

do_compile() {
}

do_install_append() {
	install -d ${D}/${includedir}
	for d in EGL GLES GLES2 GLES3 KHR; do
		install -d ${D}${includedir}/$d
		for f in ${S}/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d/
		done
	done
}

FILES_${PN}-dev = "/usr/include/*"
