SUMMARY = "tinytag is a library for reading music meta data of most common audio files in pure Python"
DESCRIPTION = "Read music meta data and length of MP3, OGG, OPUS, MP4, M4A, FLAC, WMA and Wave files"
HOMEPAGE = "https://github.com/devsnd/tinytag"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=25c35b30c9b00b08459819ae6aae6e7d"

inherit pypi setuptools3

SRC_URI[md5sum] = "bb2617566457cfb229f8e5d303a78c29"
SRC_URI[sha256sum] = "122a63b836f85094aacca43fc807aaee3290be3de17d134f5f4a08b509ae268f"

include ${PYTHON_PN}-package-split.inc
