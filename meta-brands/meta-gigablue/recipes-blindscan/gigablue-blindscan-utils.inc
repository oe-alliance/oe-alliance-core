SUMMARY = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "4.0"

PR = "r12"

PACKAGES = "gigablue-blindscan-dvbs-utils-${MACHINE} gigablue-blindscan-dvbc-utils-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbc virtual/blindscan-dvbs"
RPROVIDES_gigablue-blindscan-dvbc-utils-${MACHINE} += "virtual/blindscan-dvbc"
RPROVIDES_gigablue-blindscan-dvbs-utils-${MACHINE} += "virtual/blindscan-dvbs"

DOWNLOADMACHINE ?= "${MACHINE}"
SRC_URI = "http://impex-sat.de/gigablue/blindscan/gigablue-blindscan-utils-${SRCDATE}.tgz"

S = "${WORKDIR}/blindscan-utils"

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/gigablue_blindscan" "${D}/${bindir}"
}

FILES_gigablue-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"
FILES_gigablue-blindscan-dvbs-utils-${MACHINE} = "${bindir}/gigablue_blindscan"

INHIBIT_PACKAGE_STRIP = "1"

PACKAGE_ARCH := "${MACHINE_ARCH}"
