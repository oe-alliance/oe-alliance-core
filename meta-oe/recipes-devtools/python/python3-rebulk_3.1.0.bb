SUMMARY  = "Rebulk - Define simple search patterns in bulk to perform advanced matching on any string."
HOMEPAGE = "https://github.com/Toilal/rebulk/"
SECTION = "devel/python"
LICENSE = "BSD-4-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df5f9321c8784271adb6c95a3da69f82"

RDEPENDS:${PN} = "${PYTHON_PN}-regex"

SRC_URI += "file://disable-test.patch"

SRC_URI[md5sum] = "ce9e0f02e5e812859a479d6536543f41"
SRC_URI[sha256sum] = "809de3a97c68afa831f7101b10d316fe62e061dc9f7f67a44b7738128721173a"

S = "${WORKDIR}/rebulk-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
