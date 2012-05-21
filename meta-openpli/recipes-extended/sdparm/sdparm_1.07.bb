SUMMARY = "The sdparm utility accesses SCSI device parameters"
SECTION = "console"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=ecab6c36b7ba82c675581dd0afde36f7"
PR = "r1"

SRC_URI = "http://sg.danny.cz/sg/p/sdparm-${PV}.tgz"
SRC_URI[md5sum] = "c807f9db3dd7af175214be0d7fece494"
SRC_URI[sha256sum] = "c1d257ba97f37f99a602e889a73e4c62f9b374c5b979e33fc06963462f0c0e41"

inherit autotools
