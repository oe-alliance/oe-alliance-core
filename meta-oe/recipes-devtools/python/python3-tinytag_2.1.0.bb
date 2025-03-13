SUMMARY = "tinytag is a library for reading music meta data of most common audio files in pure Python"
DESCRIPTION = "Read music meta data and length of MP3, OGG, OPUS, MP4, M4A, FLAC, WMA and Wave files"
HOMEPAGE = "https://github.com/devsnd/tinytag"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b37f9f84c13eedf9ff42f49135da6ed"

inherit pypi python_setuptools_build_meta

SRC_URI[md5sum] = "547292db05de2ec6002678b9124246d2"
SRC_URI[sha256sum] = "9a1b2e37aa45723541621133004ae86416086a0b1922e600cff5bfca5ef93e55"

include python3-package-split.inc
