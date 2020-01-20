SUMMARY = "Self-contained ISO 3166-1 country definitions"
DESCRIPTION = "ISO 3166-1 defines two-letter, three-letter, and three-digit country codes. \
                Python-iso3166 is a self-contained module that converts between these codes \
                and the corresponding country name."
HOMEPAGE = "http://github.com/deactivated/python-iso3166"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=5e2f4edc7e7408a82e4a1d05f229b695"

SRC_URI[md5sum] = "53c313c7ae8721e40ddd5e7a01bbcb7e"
SRC_URI[sha256sum] = "b1e58dbcf50fbb2c9c418ec7a6057f0cdb30b8f822ac852f72e71ba769dae8c5"

inherit pypi setuptools

PACKAGES = "${PN}"

BBCLASSEXTEND = "native nativesdk"
