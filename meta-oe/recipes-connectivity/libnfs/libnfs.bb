SUMMARY = "LIBNFS is a client library for accessing NFS shares over a network."
SECTION = "network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=a8375400e512ca525d061f32fb28b83a"

inherit autotools

PR = "r1"

SRCREV="libnfs-1.9.2"

SRC_URI = "git://github.com/sahlberg/libnfs;protocol=git;branch=master;tag=${SRCREV} \
"
S = "${WORKDIR}/git"
