SUMMARY = "Objects and routines pertaining to date and time (tempora)"
HOMEPAGE = "https://github.com/jaraco/tempora"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"
DEPENDS += "python3-setuptools-scm-native"

SRC_URI[md5sum] = "ca7fb7c9523fb5232b734a19dae05c6a"
SRC_URI[sha256sum] = "1e9606e65a3f2063460961d68515dee07bdaca0859305a8d3e6604168175fef1"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
