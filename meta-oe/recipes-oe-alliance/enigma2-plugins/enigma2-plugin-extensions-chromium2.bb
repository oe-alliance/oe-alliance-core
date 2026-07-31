SUMMARY = "Chromium Web Browser"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/python/python3-compileall.inc

SRCDATE = "20250102"
SRC_URI[md5sum] = "4f8a9674792305a319c4f17fcd1f518f"
SRC_URI[sha256sum] = "dc990f0a89e671f03edd2979e0254a816b51bcaff92f4e7ba303360c3db2a444"

PV = "1.0+${SRCDATE}"
PR = "r0"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_package_qa[noexec] = "1"

INSANE_SKIP:${PN} += "already-stripped"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

RREPLACES:enigma2-plugin-extensions-chromium = "enigma2-plugin-extensions-netflix"
RCONFLICTS:enigma2-plugin-extensions-chromium = "enigma2-plugin-extensions-netflix"

RDEPENDS:${PN}  = "virtual-libgles2"

SRC_URI = "http://source.mynonpublic.com/netflix/chromium_${SRCDATE}.tar.gz"

S = "${UNPACKDIR}"

FILES:${PN} =  "${bindir} ${libdir} /usr/share/netflix"

do_install() {
	install -d ${D}/usr/share/netflix
	install -d ${D}/usr/share/netflix/nss
	install -d ${D}/usr/share/netflix/locales
	install -d ${D}/usr/share/netflix/swiftshader
	install -d ${D}/usr/share/netflix/ui
	install -d ${D}/usr/share/netflix/test_fonts

	install -m 0755 ${S}/*.so* ${D}/usr/share/netflix
	install -m 0755 ${S}/content_shell.pak ${D}/usr/share/netflix
	install -m 0755 ${S}/content_shell.pak.info ${D}/usr/share/netflix
	install -m 0755 ${S}/ui_resources_100_percent.pak ${D}/usr/share/netflix
	install -m 0755 ${S}/bin ${D}/usr/share/netflix/bin
	install -m 0755 ${S}/prepare.sh ${D}/usr/share/netflix
	install -m 0755 ${S}/plugin_ol.config ${D}/usr/share/netflix/

	install -m 0755 ${S}/nss/* ${D}/usr/share/netflix/nss
	install -m 0755 ${S}/swiftshader/* ${D}/usr/share/netflix/swiftshader
	install -m 0755 ${S}/ui/* ${D}/usr/share/netflix/ui
	install -m 0755 ${S}/test_fonts/* ${D}/usr/share/netflix/test_fonts

	install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/Chromium
	cp -r --no-preserve=ownership ${S}/plugin/* ${D}${libdir}/enigma2/python/Plugins/Extensions/Chromium

	chmod -R a+rX ${D}/usr/share/netflix
}

pkg_postinst:ontarget:${PN}(){
#!/bin/sh
ln -s /usr/share/netflix/bin /usr/bin/netflix
rm -rf /home/root/.config 
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/Chromium
rm -rf /usr/share/netflix
rm -f /usr/bin/netflix
rm -rf /home/root/.config
exit 0
}
