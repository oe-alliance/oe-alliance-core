SUMMARY = "tinytag is a library for reading music meta data of most common audio files in pure Python"
DESCRIPTION = "Read music meta data and length of MP3, OGG, OPUS, MP4, M4A, FLAC, WMA and Wave files"
HOMEPAGE = "https://github.com/devsnd/tinytag"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b37f9f84c13eedf9ff42f49135da6ed"

inherit pypi python_setuptools_build_meta

SRC_URI[md5sum] = "04d0a815b5c711eac96b1d3013c1ff1d"
SRC_URI[sha256sum] = "b417d480cf3b0c2d60a3afef705b29ac0080fc72d35b0b579b64184c54ee394c"

include python3-package-split.inc
