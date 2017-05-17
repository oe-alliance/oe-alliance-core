SUMMARY = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"

PR = "r0"

PACKAGES = "gigablue-blindscan-dvbs-utils-arm-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_gigablue-blindscan-dvbs-utils-arm-${MACHINE} += "virtual/blindscan-dvbs"

DOWNLOADMACHINE ?= "${MACHINE}"
SRC_URI = "http://archiv.openmips.com/beta/gigablue-blindscan-utils-arm-${SRCDATE}.zip"

S = "${WORKDIR}/blindscan-utils"

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/gigablue_blindscan" "${D}/${bindir}"
}

FILES_gigablue-blindscan-dvbs-utils-arm-${MACHINE} = "${bindir}/gigablue_blindscan"

INHIBIT_PACKAGE_STRIP = "1"

PACKAGE_ARCH := "${MACHINE_ARCH}"
