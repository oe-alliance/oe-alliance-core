SUMMARY = "The official Python interface to the Flickr API"
HOMEPAGE = "http://stuvel.eu/flickrapi"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=adb72080efb3467253d1ecbbbe779f17"

inherit pypi setuptools

SRC_URI[md5sum] = "94e9b9ac81b1dae1b226e22ac6a4375b"
SRC_URI[sha256sum] = "ffb27b0e4f6d3ae8fc7d20e4696d2de2f95093ecf665a93d7f1d5a3383f8e20d"

include python-package-split.inc
