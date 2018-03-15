DESCRIPTION = "libgles mali headers"
LICENSE = "CLOSED"

PR = "r1"

SRC_URI = "http://source.mynonpublic.com/zgemma/airdigital-mali-headers.zip"

SRC_URI[md5sum] = "361acd66c85bab73c6d6c5292f42b45f"
SRC_URI[sha256sum] = "df1ad1b4bf37db3e429ffe61d76df248dbeb16fe26c44856f054a6a80396e9a2"

DEPENDS += "libomxil"

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