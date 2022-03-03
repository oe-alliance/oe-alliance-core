SUMMARY = "ISO country, subdivision, language, currency and script definitions and their translations"
HOMEPAGE = ""
AUTHOR = "Christian Theune <ct@flyingcircus.io>"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2e3f24d82bf0f89a5a3be6801b999bf1"

include python3-package-split.inc

inherit setuptools3

SRC_URI = "https://files.pythonhosted.org/packages/76/73/6f1a412f14f68c273feea29a6ea9b9f1e268177d32e0e69ad6790d306312/pycountry-20.7.3.tar.gz \
            file://version.patch"
SRC_URI[md5sum] = "55b4727c4ba0219105289c1ce6c9cde0"
SRC_URI[sha256sum] = "81084a53d3454344c0292deebc20fcd0a1488c136d4900312cbd465cf552cb42"

S = "${WORKDIR}/pycountry-${PV}"
