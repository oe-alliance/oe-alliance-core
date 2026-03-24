SUMMARY = "Composable complex class support for attrs and dataclasses."
HOMEPAGE = "https://github.com/Tinche/cattrs"
AUTHOR = "Tin Tvrtkovic <tinchester@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=12efd5ce6c6c43c4ead370bd15f93560"

DEPENDS += "python3-hatch-vcs-native"

inherit pypi python_hatchling

SRC_URI[md5sum] = "0dfeb8a55487c3aa7ae27489b72f0c68"
SRC_URI[sha256sum] = "fa239e0f0ec0715ba34852ce813986dfed1e12117e209b816ab87401271cdd40"

RDEPENDS:${PN} = "python3-attrs"

include python3-package-split.inc
