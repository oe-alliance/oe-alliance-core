SUMMARY = "Composable complex class support for attrs and dataclasses."
HOMEPAGE = "https://github.com/Tinche/cattrs"
AUTHOR = "Tin Tvrtkovic <tinchester@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=12efd5ce6c6c43c4ead370bd15f93560"

DEPENDS += "python3-hatch-vcs-native"

inherit pypi python_hatchling

SRC_URI[md5sum] = "58b4144b2420e9512f7c29c8d8181d7c"
SRC_URI[sha256sum] = "c914b734e0f2d59e5b720d145ee010f1fd9a13ee93900922a2f3f9d593b8382c"

RDEPENDS:${PN} = "python3-attrs"

include python3-package-split.inc
