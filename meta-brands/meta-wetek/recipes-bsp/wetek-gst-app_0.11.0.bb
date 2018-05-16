SUMMARY = "GStreamer AML player app"
SECTION = "multimedia"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r1"

DEPENDS = "gstreamer libffi zlib libxml2 glib-2.0"

SRC_URI = "file://gst-app-0.11.0.zip"

S = "${WORKDIR}/gst-app-0.11.0"

inherit autotools pkgconfig

do_configure_prepend() {
    # This m4 file contains nastiness which conflicts with libtool 2.2.2
    rm ${S}/m4/lib-link.m4 || true
}
