SUMMARY = "ET LiveStream Importer"
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

RDEPENDS_${PN} = "${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugins-ugly-asf gstreamer1.0-plugins-bad-mms gstreamer1.0-plugins-good-rtsp gstreamer1.0-plugins-good-flv gstreamer1.0-plugins-bad-rtmp gst-plugin-libxt", "gst-plugins-ugly-asf gst-plugins-bad-mms gst-plugins-good-rtsp gst-plugins-good-flv gst-plugins-bad-rtmp gst-plugin-libxt", d)}"

FILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/EtLiveStream"

do_install_append() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/EtLiveStream
    install -m 0644 ${S}/__init__.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/EtLiveStream
    install -m 0644 ${S}/plugin.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/EtLiveStream
}
