SUMMARY = "A Python wrapper for The Movie Database API v3"
HOMEPAGE = "https://github.com/celiao/tmdbsimple"
AUTHOR = "Celia Oakley <celia.oakley@alumni.stanford.edu>"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=783b7e40cdfb4a1344d15b1f7081af66"

SRC_URI = "https://files.pythonhosted.org/packages/a1/87/3309cb03df1c9f9895fccd87e9875050f19e2cfec5a50b9d72e50d420fc8/tmdbsimple-2.9.1.tar.gz"
SRC_URI[md5sum] = "65ff24381fded5c5430f30022f367584"
SRC_URI[sha256sum] = "636eaaaec82027929e8a91c2166e01f552ec63f869bf1fcd65aa561b705c7464"

S = "${WORKDIR}/tmdbsimple-2.9.1"

RDEPENDS:${PN} = "python3-requests"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
