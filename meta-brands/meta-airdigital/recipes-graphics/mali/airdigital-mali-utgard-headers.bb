DESCRIPTION = "libgles mali headers"
LICENSE = "CLOSED"

PR = "r2"

SRC_URI = "http://source.mynonpublic.com/zgemma/airdigital-mali-utgard-headers.zip"

SRC_URI[md5sum] = "8807a06a6d6ac5134868085400e0fcdd"
SRC_URI[sha256sum] = "2876d365c69d80e3e97a2547a8326ea392611523187324c4314ba50510fedda1"

DEPENDS += "libomxil"

S = "${WORKDIR}"

do_compile() {
}

do_install_append() {
	install -d ${D}${includedir}
	for d in EGL GLES GLES2 KHR; do
		install -d ${D}${includedir}/$d
		for f in ${S}/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d/
		done
	done
}

FILES_${PN}-dev = "${includedir}/*"