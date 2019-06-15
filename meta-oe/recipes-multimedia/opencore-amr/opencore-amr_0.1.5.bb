SUMMARY = "OpenCORE AMR decoder library"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dd2c2486aca02190153cf399e508c7e7"

SRC_URI = "${SOURCEFORGE_MIRROR}/${BPN}/${BPN}-${PV}.tar.gz"

S = "${WORKDIR}/${BPN}-${PV}"

inherit autotools

SRC_URI[md5sum] = "e0798587b91411cc092aa73091a97dfc"
SRC_URI[sha256sum] = "2c006cb9d5f651bfb5e60156dbff6af3c9d35c7bbcc9015308c0aff1e14cd341"
