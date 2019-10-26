SUMMARY = "Netflix for chromium browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20191021"
SRC_URI[md5sum] = "31d4e838604bf2e519022084f1cee262"
SRC_URI[sha256sum] = "3a106d86774bea509aced655d9bd0e531134aabb323dd5f4bf9657c92442f2d9"

PV = "1.0+${SRCDATE}"
PR = "r1"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_package_qa[noexec] = "1"

INSANE_SKIP_${PN} += "already-stripped"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""


RDEPENDS_${PN}  = "qtwebkit virtual/libgles2"

SRC_URI = "http://source.mynonpublic.com/netflix/netflix_browser_${SRCDATE}.zip"

S = "${WORKDIR}/Netflix"

FILES_${PN} =  "${bindir} ${libdir} /usr/share/netflix"

do_install() {
	install -d ${D}/usr/share/netflix
	install -d ${D}/usr/share/netflix/html
	install -d ${D}/usr/share/netflix/lib
	install -d ${D}/usr/share/netflix/lib/hisi3798mv200
	install -d ${D}/usr/share/netflix/lib/hisi3798mv200/nss
	install -d ${D}/usr/share/netflix/locales
	install -m 0755 ${S}/*.so ${D}/usr/share/netflix
	install -m 0755 ${S}/prepare.sh ${D}/usr/share/netflix
	install -m 0755 ${S}/content_shell.pak ${D}/usr/share/netflix
	install -m 0755 ${S}/content_shell.pak.info ${D}/usr/share/netflix
	install -m 0755 ${S}/shell_resources.pak ${D}/usr/share/netflix
	install -m 0755 ${S}/ui_resources_100_percent.pak ${D}/usr/share/netflix
	install -m 0755 ${S}/cr_main ${D}/usr/share/netflix/bin
	install -m 0755 ${S}/html/* ${D}/usr/share/netflix/html
	install -m 0755 ${S}/lib/hisi3798mv200/lib* ${D}/usr/share/netflix/lib/hisi3798mv200
	install -m 0755 ${S}/lib/hisi3798mv200/nss/* ${D}/usr/share/netflix/lib/hisi3798mv200/nss

	install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/Netflix
	install -m 0755 ${S}/plugin/*.py ${D}${libdir}/enigma2/python/Plugins/Extensions/Netflix
	install -m 0755 ${S}/plugin/*.png ${D}${libdir}/enigma2/python/Plugins/Extensions/Netflix
	cp -rp ${S}/plugin/locale ${D}${libdir}/enigma2/python/Plugins/Extensions/Netflix

	chmod -R a+rX ${D}/usr/share/netflix
}

pkg_postinst_${PN}(){
#!/bin/sh
ln -s /usr/share/netflix/bin /usr/bin/netflix
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/Netflix
rm -rf /usr/share/netflix
rm -f /usr/bin/netflix
exit 0
}
