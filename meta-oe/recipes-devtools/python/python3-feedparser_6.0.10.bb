SUMMARY = "Universal feed parser, handles RSS 0.9x, RSS 1.0, RSS 2.0, CDF, Atom 0.3, and Atom 1.0 feeds"
HOMEPAGE = "https://feedparser.readthedocs.io/en/latest/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4940c38d79bc5dca0630466d7c54b004"

SRC_URI[md5sum] = "45716da638688577c7b0587a0a839a21"
SRC_URI[sha256sum] = "27da485f4637ce7163cdeab13a80312b93b7d0c1b775bef4a47629a3110bca51"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
