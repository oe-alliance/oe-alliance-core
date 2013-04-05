DESCRIPTION = "opera-hbbtv-browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

DEPENS = "tslib mpfr gmp "
RDEPENS = "tslib-conf libts-1.0-0 libsysfs2 libgmp3 libmpfr1 "

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_DATE = "20130122_1"
SRC_URI = "http://code.vuplus.com/download/build.fc3abf29fb03f797e78f907928125638/embedded/opera-sdk-build-package/opera-hbbtv_${SRC_DATE}.tar.gz"

PR = "r13_${SRC_DATE}"

S = "${WORKDIR}/opera-hbbtv"

do_install() {
	install -d ${D}/usr/local/hbb-browser
	cp -avR ${S}/opera/* ${D}/usr/local/hbb-browser/

	install -d ${D}/etc
	cp -avR ${S}/dfb/etc/* ${D}/etc/

	install -d ${D}/usr/bin
	cp -avR ${S}/dfb/usr/bin/* ${D}/usr/bin/

	install -d ${D}/usr/lib
	cp -avR ${S}/dfb/usr/lib/* ${D}/usr/lib/

	install -d ${D}/usr/share
	cp -avR ${S}/dfb/usr/share/* ${D}/usr/share/

	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/HbbTV
	cp -avR ${S}/plugin/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/HbbTV
}

# Just a quick hack to "compile" the python parts.
do_compile_append() {
	python -O -m compileall ${S}
}

INHIBIT_PACKAGE_STRIP = "1"

PACKAGES =+ "${PN}-src"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.py"
FILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.pyo \
			   /usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.so \
			   /usr/lib/directfb*.* \
			   /usr/lib/lib*.* \
			   /usr/local \
			   /usr/share \
			   /usr/bin \
			   /etc "

SRC_URI[md5sum] = "ae2d63b91728367210547a2aa8612daf"
SRC_URI[sha256sum] = "39230e0f70337ed4c02077b0bb8f0c1c7adc9ed756e41141ca4a3971c9453d81"