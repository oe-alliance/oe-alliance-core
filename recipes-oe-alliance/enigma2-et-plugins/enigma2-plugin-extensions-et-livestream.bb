DESCRIPTION = "ET LiveStream Importer"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "optional"

LIC_FILES_CHKSUM = "file://LICENSE.GPLv2;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/et-plugins/et-live-links.git;protocol=git"
S = "${WORKDIR}/git"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

RDEPENDS_${PN} = "gst-plugins-ugly-asf gst-plugins-bad-mms gst-plugins-good-rtsp gst-plugins-good-flv gst-plugins-bad-rtmp gst-plugin-libxt"

FILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/EtLiveStream"

do_install_append() {
	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/EtLiveStream
	install -m 0644 ${S}/__init__.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/EtLiveStream
	install -m 0644 ${S}/plugin.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/EtLiveStream
}
