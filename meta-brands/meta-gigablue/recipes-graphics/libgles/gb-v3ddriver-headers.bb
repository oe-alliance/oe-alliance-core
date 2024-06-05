DESCRIPTION = "libgles v3ddriver headers"
LICENSE = "CLOSED"

PR = "r1"

SRC_URI = "https://source.mynonpublic.com/gigablue/v3ddriver/gb-nexus-headers.zip"

SRC_URI[md5sum] = "cd28a6e2d862354c2854b9278fd32365"
SRC_URI[sha256sum] = "4cfda443d72ec56965f989b9306c0af6f85cbac55fc6a70b0d081ea605c192aa"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install:append() {
	install -d ${D}/${includedir}
	for f in ${S}/*.h; do
		install -m 0644 $f ${D}${includedir}/
	done
	for d in EGL GLES GLES2 GLES3 KHR; do
		install -d ${D}${includedir}/$d
		for f in ${S}/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d/
		done
	done
}

FILES:${PN}-dev = "/usr/include/*"
