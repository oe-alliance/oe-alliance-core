DESCRIPTION = "Stateful programmatic web browsing, after Andy Lester's Perl module WWW::Mechanize."
HOMEPAGE = "http://wwwsearch.sourceforge.net/mechanize/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=621053d4e9daec9454e15e60fe40214f"

PR = "r2"

RDEPENDS_${PN} = "python-core python-robotparser"

SRC_URI = "https://pypi.python.org/packages/source/m/mechanize/mechanize-${PV}.tar.gz"

SRC_URI[md5sum] = "7c771ff38cced05a6153b56a155f5b76"
SRC_URI[sha256sum] = "6355c11141f6d4b54a17fc2106944806b5db2711e60b120d15d83db438c333fd"

S = "${WORKDIR}/mechanize-${PV}"

inherit setuptools distutils

include python-package-split.inc
