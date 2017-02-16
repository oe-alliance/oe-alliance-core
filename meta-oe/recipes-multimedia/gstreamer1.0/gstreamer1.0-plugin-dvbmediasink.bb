DESCRIPTION = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base libdca"

GSTVERSION = "1.0"

#SRC_URI = "git://git.code.sf.net/p/openpli/gst-plugin-dvbmediasink;protocol=git;branch=gst-1.0"
SRC_URI = "git://github.com/christophecvr/gstreamer1.0-plugin-multibox-dvbmediasink.git;protocol=git"

SRC_URI_append_dags7335 = " \
    file://0001-update-dags-support.patch;patch=1 \ 
"
SRC_URI_append_dags7356 = " \
    file://0001-update-dags-support.patch;patch=1 \ 
"
SRC_URI_append_dags7362 = " \
    file://0001-update-dags-support.patch;patch=1 \
"

SRC_URI_append_dm7080 = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_dm820 = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_dm520 = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_dm900 = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_vusolo4k = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_vuuno4k = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_vuultimo4k = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_xc7362 = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_xc7346 = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_xc7439 = " \
    file://0001-add-VB6-VB8-VB9-SPARK-TYPE2.patch;patch=1 \
"

SRC_URI_append_gb73625 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_g100 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_ch625lc = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_hd11 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_hd1265 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_vs1000 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_hd1500 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_h5 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"


SRC_URI_append_hd51 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_hd52 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_vs1500 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_7005s = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_7105s = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_7215s = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_7225s = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_dags73625 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_formuler4turbo = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

SRC_URI_append_sf4008 = " \
    file://0001-add-vp8-vp9-vp6-spark.patch;patch=1 \
"

S = "${WORKDIR}/git"

inherit gitpkgv

PV = "${GSTVERSION}+git${SRCPV}"
PKGV = "${GSTVERSION}+git${GITPKGV}"
PR = "r14"

do_configure_prepend() {
    sed -i 's/AC_INIT.*$/AC_INIT(gst-plugin-dvbmediasink, 1.0.0, @pli4)/' ${S}/configure.ac
    sed -i 's/AM_INIT_AUTOMAKE.*$/AM_INIT_AUTOMAKE([foreign])/' ${S}/configure.ac
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/gstreamer-${GSTVERSION}/*.so* ${sysconfdir}/gstreamer/aactranscode"
FILES_${PN}-dev += "${libdir}/gstreamer-${GSTVERSION}/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-${GSTVERSION}/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-${GSTVERSION}/.debug"

EXTRA_OECONF = "${DVBMEDIASINK_CONFIG} --with-gstversion=${GSTVERSION}"
