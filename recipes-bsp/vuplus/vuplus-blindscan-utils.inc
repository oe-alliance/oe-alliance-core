DESCRIPTION = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PACKAGES = "vuplus-blindscan-dvbs-utils-${MACHINE} vuplus-blindscan-dvbc-utils-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_vuplus-blindscan-dvbs-utils-${MACHINE} += "virtual/blindscan-dvbs"
RPROVIDES_vuplus-blindscan-dvbc-utils-${MACHINE} += "virtual/blindscan-dvbc"

DOWNLOADMACHINE ?= "${MACHINE}"
SRC_URI = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${DOWNLOADMACHINE}-${PV}.tar.bz2"

PV = "3.2"
PR = "r4"

S = "${WORKDIR}/blindscan-utils"

FILES_vuplus-blindscan-dvbs-utils-${MACHINE} = "${bindir}/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"

INHIBIT_PACKAGE_STRIP = "1"

PACKAGE_ARCH := "${MACHINE_ARCH}"
