SUMMARY = "decoding library for DTS Coherent Acoustics streams"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r1"

inherit autotools

SRC_URI = "https://mirror.nl.leaseweb.net/videolan/libdca/${PV}/libdca-${PV}.tar.bz2"

S = "${WORKDIR}/libdca-${PV}"

SRC_URI[md5sum] = "eb65623c2b9b4417aada8013760b090a"
SRC_URI[sha256sum] = "98f98a9aa000a26b927c6facd15d18dcf664238adfc5db24f533c5932cdb1f40"

