SUMMARY = "tinytag is a library for reading music meta data of most common audio files in pure Python"
DESCRIPTION = "Read music meta data and length of MP3, OGG, OPUS, MP4, M4A, FLAC, WMA and Wave files"
HOMEPAGE = "https://github.com/devsnd/tinytag"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=10484ba6e601ff58e4c1dcef1f4f59db"

inherit pypi python_setuptools_build_meta

SRC_URI[md5sum] = "44b204c2dbb187311864fb348687e2c2"
SRC_URI[sha256sum] = "e6d06610ebe7cd66fd07be2d3b9495914ab32654a5e47657bb8cd44c2484523c"

include python3-package-split.inc
