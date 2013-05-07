DESCRIPTION = "opera-hbbtv-browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

DEPENS = "tslib mpfr gmp "
RDEPENS = "tslib-conf libts-1.0-0 libsysfs2 libgmp3 libmpfr1 "

PACKAGE_ARCH := "${MACHINE_ARCH}"
PACKAGES =+ "${PN}-src enigma2-hbbtv-util enigma2-hbbtv-util-src"
PROVIDES =+ "enigma2-hbbtv-util"

# SRC_DATE = "20130502_0"
SRC_DATE = "20121128_0"
SRC_URI = "http://code.vuplus.com/download/build.fc3abf29fb03f797e78f907928125638/embedded/opera-sdk-build-package/opera-hbbtv_${SRC_DATE}.tar.gz"

PR = "r19_${SRC_DATE}"

S = "${WORKDIR}/opera-hbbtv"

INHIBIT_PACKAGE_STRIP = "1"

# Just a quick hack to "compile" the python parts.
do_compile_append() {
	python -O -m compileall ${S}
}

do_install() {
	install -d ${D}/usr/local/hbb-browser
	mv ${S}/opera/lib/libopera.so ${S}/opera/lib/libopera.so.0
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

do_package_qa() {
}

FILES_${PN} = "/usr /etc"
FILES_enigma2-hbbtv-util = "/usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.pyo /usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.so"
FILES_enigma2-hbbtv-util-src = "/usr/lib/enigma2/python/Plugins/Extensions/HbbTV/*.py"

# SRC_URI[md5sum] = "7204505914945da89ace49556218f0ef"
# SRC_URI[sha256sum] = "d42d671dfebb3c5125dccf3b72e1cfbb957682d9e497c2dc7a26bdd119118d87"
SRC_URI[md5sum] = "5a8bf37321bcb2c8b23b4538d3862e87"
SRC_URI[sha256sum] = "4e3a41188b81a871d7f0bf559d1cd0efc5f2faded9d37ca81c4e28e0675b5178"
