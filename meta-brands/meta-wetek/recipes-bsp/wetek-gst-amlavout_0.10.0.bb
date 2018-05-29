SUMMARY = "GStreamer AML AVout plugin"
# Freescale rip-off
#  Amlogic GStreamer plugins to send audio es to aml dsp and video es to aml hw decoder. 
#  decode and render will be complete at kernel level.
SECTION = "multimedia"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"
PARALLEL_MAKE = ""

inherit autotools pkgconfig

DEPENDS = "gstreamer wetek-gst-amlavsink wetek-libamcodec-${MACHINE} wetek-libamavutils-${MACHINE} wetek-libamplayer-${MACHINE}"
RDEPENDS_{PN} = "wetek-libamavutils-${MACHINE}"

SRC_URI = " file://gst-aml-plugins-0.10.0.zip "

S = "${WORKDIR}/gst-aml-plugins-0.10.0"

CFLAGS =+ " -O2 -fPIC -I${STAGING_INCDIR} -I${S}/include  -I${S}/include/amports  -I${S}/common/include/ppmgr \
-I${S}/amlaudio -I${S}/amlvideo "
LDFLAGS =+ " -L${STAGING_LIBDIR} -L${STAGING_BASELIBDIR} " 

FILES_${PN} += "${libdir}"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug $"
FILES_${PN}-dev += "${S}/include/* "

do_rm_work(){
}

do_package_qa() {
}
