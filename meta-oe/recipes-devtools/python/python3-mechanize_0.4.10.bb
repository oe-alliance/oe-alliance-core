SUMMARY = "Stateful programmatic web browsing, after Andy Lester's Perl module WWW::Mechanize."
HOMEPAGE = "https://github.com/python-mechanize/mechanize"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=621053d4e9daec9454e15e60fe40214f"

RDEPENDS:${PN} = "${PYTHON_PN}-core"

SRC_URI[md5sum] = "8ccc66d8b049951d477fb20b204d545f"
SRC_URI[sha256sum] = "1dea947f9be7ea0ab610f7bbc4a4e36b45d6bfdfceea29ad3d389a88a1957ddf"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
