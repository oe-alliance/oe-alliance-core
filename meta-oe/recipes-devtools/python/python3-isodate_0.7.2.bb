SUMMARY = "An ISO 8601 date/time/duration parser and formatter"
HOMEPAGE = "https://github.com/gweis/isodate/"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=810c800df2e786f77d64c50af3bde298"

DEPENDS += "python3-setuptools-scm-native"

SRC_URI[sha256sum] = "4cd1aa0f43ca76f4a6c6c0292a85f40b35ec2e43e315b59f06e6d32171a953e6"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN} += " \
    python3-numbers \
    python3-six \
"

BBCLASSEXTEND = "native nativesdk"

include python3-package-split.inc
