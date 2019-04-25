SUMMARY = "Self-contained ISO 3166-1 country definitions"
DESCRIPTION = "ISO 3166-1 defines two-letter, three-letter, and three-digit country codes. python-iso3166 is a self-contained module that converts between these codes and the corresponding country name."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=5e2f4edc7e7408a82e4a1d05f229b695"

inherit pypi setuptools

PACKAGES = "${PN}"

SRC_URI[md5sum] = "8ac36e51e7798803066f8ead9c94deff"
SRC_URI[sha256sum] = "eaad12d1c5fb9394dc423a13b8084973960a7b392677039ce6fd932aa4a74bab"

BBCLASSEXTEND = "native nativesdk"
