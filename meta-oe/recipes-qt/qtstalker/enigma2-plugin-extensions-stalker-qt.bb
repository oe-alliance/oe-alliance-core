SUMMARY = "QT Stalker"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20180227"

PV = "1.4"
PR = "${SRCDATE}"

SRC_URI = "file://stalker-103.zip"
SRC_URI_h9 = "file://stalker2-104.zip"

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

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/Stalker
exit 0
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "already-stripped"
