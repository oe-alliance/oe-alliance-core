SUMMARY = "Composable complex class support for attrs and dataclasses."
HOMEPAGE = "https://github.com/Tinche/cattrs"
AUTHOR = "Tin Tvrtkovic <tinchester@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=12efd5ce6c6c43c4ead370bd15f93560"

DEPENDS += "${PYTHON_PN}-hatch-vcs-native"

inherit pypi python_hatchling

SRC_URI[md5sum] = "555a80a76a06708adc793ceab25326b9"
SRC_URI[sha256sum] = "a934090d95abaa9e911dac357e3a8699e0b4b14f8529bcc7d2b1ad9d51672b9f"

RDEPENDS:${PN} = "${PYTHON_PN}-attrs"

include ${PYTHON_PN}-package-split.inc
