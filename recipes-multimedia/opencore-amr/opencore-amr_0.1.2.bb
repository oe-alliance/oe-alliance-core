DESCRIPTION = "OpenCORE AMR decoder library"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=dd2c2486aca02190153cf399e508c7e7"

PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/${PN}/${PN}-${PV}.tar.gz"

S = "${WORKDIR}/${PN}-${PV}"

inherit autotools

SRC_URI[md5sum] = "8e8b8b253eb046340ff7b6bf7a6ccd3e"
SRC_URI[sha256sum] = "277d0c13b722c1bae6ebdf8959df7b55129f8e005edd12f5d2c38baf76ba71a9"
