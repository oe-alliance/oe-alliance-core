SUMMARY = "QT Stalker"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20180106"

PV = "1.2"
PR = "${SRCDATE}"

SRC_URI = "file://stalker-102.zip"

RDEPENDS_${PN}  = "qtwebkit virtual/libgles2 python-netifaces" 

S = "${WORKDIR}/files"

FILES_${PN} =  "${bindir} ${libdir}"

do_install(){
	install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/Stalker
	install -m 0755 ${S}/*.py ${D}${libdir}/enigma2/python/Plugins/Extensions/Stalker
	install -m 0755 ${S}/*.png ${D}${libdir}/enigma2/python/Plugins/Extensions/Stalker


	install -d ${D}/${bindir}
	install -m 0755 ${S}/stalker ${D}/${bindir}

	install -d ${D}/locale
	cp -rp ${S}/locale ${D}${libdir}/enigma2/python/Plugins/Extensions/Stalker
}

pkg_postinst_${PN}(){
#!/bin/sh
ln -sf /usr/share/fonts /usr/lib/fonts
exit 0
}


INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "already-stripped"
