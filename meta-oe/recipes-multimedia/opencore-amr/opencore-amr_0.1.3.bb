SUMMARY = "OpenCORE AMR decoder library"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=dd2c2486aca02190153cf399e508c7e7"

PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/${BPN}/${BPN}-${PV}.tar.gz"

S = "${WORKDIR}/${BPN}-${PV}"

inherit autotools

SRC_URI[md5sum] = "09d2c5dfb43a9f6e9fec8b1ae678e725"
SRC_URI[sha256sum] = "106bf811c1f36444d7671d8fd2589f8b2e0cca58a2c764da62ffc4a070595385"
