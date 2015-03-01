DESCRIPTION = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base libdca ${@base_contains("BRAND_OEM", "fulan", "fulan-dvb-modules" , "", d)}"
RDEPENDS_${PN} = "gstreamer1.0-libav"

GSTVERSION = "1.0"

SRC_URI = "git://git.code.sf.net/p/openpli/gst-plugin-dvbmediasink;protocol=git;branch=gst-1.0"

SRC_URI_append_sh4 = " \
    file://dvbmediasink_sh4_fix.patch;patch=1 \
"
SRC_URI_append_dags1 = " \
    file://gst_audio_flac_wma_wmv.patch;patch=1 \ 
"
SRC_URI_append_dags2 = " \
    file://gst_audio_flac_wma_wmv.patch;patch=1 \ 
"
SRC_URI_append_dags3 = " \
    file://gst_audio_flac_wma_wmv.patch;patch=1 \ 
"
SRC_URI_append_dags4 = " \
    file://gst_audio_flac_wma_wmv.patch;patch=1 \ 
"
SRC_URI_append_dags5 = " \
    file://gst_audio_flac_wma_wmv.patch;patch=1 \ 
"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit gitpkgv

PV = "${GSTVERSION}+git${SRCPV}"
PKGV = "${GSTVERSION}+git${GITPKGV}"
PR = "r1"

do_configure_prepend() {
    sed -i 's/AC_INIT.*$/AC_INIT(gst-plugin-dvbmediasink, 1.0.0, @pli4)/' ${S}/configure.ac
    sed -i 's/AM_INIT_AUTOMAKE.*$/AM_INIT_AUTOMAKE([foreign])/' ${S}/configure.ac
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/gstreamer-${GSTVERSION}/*.so*"
FILES_${PN}-dev += "${libdir}/gstreamer-${GSTVERSION}/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-${GSTVERSION}/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-${GSTVERSION}/.debug"

EXTRA_OECONF = "${DVBMEDIASINK_CONFIG} --with-gstversion=${GSTVERSION}"
