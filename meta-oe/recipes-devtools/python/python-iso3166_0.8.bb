SUMMARY = "Self-contained ISO 3166-1 country definitions"
DESCRIPTION = "ISO 3166-1 defines two-letter, three-letter, and three-digit country codes. python-iso3166 is a self-contained module that converts between these codes and the corresponding country name."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=5e2f4edc7e7408a82e4a1d05f229b695"

inherit pypi setuptools

PACKAGES = "${PN}"

SRC_URI[md5sum] = "9b7a42df80495fe253d6edd035974128"
SRC_URI[sha256sum] = "fbeb17bed90d15b1f6d6794aa2ea458e5e273a1d29b6f4939423c97640e14933"

BBCLASSEXTEND = "native nativesdk"
