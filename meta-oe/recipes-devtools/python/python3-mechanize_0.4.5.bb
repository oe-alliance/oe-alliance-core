SUMMARY = "Stateful programmatic web browsing, after Andy Lester's Perl module WWW::Mechanize."
HOMEPAGE = "https://github.com/python-mechanize/mechanize"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=621053d4e9daec9454e15e60fe40214f"

RDEPENDS:${PN} = "${PYTHON_PN}-core"

SRC_URI = "https://files.pythonhosted.org/packages/77/1b/7e4b644108e4e99b136e52c6aae34873fcd267e3d2489f3bd2cff8655a59/mechanize-${PV}.tar.gz"
SRC_URI[md5sum] = "7c771ff38cced05a6153b56a155f5b76"
SRC_URI[sha256sum] = "6355c11141f6d4b54a17fc2106944806b5db2711e60b120d15d83db438c333fd"

S = "${WORKDIR}/mechanize-${PV}"

inherit setuptools3

FILES:${PN}-src:append = " \
    ${libdir}/${PYTHON_DIR}/site-packages/mechanize-0.4.5-py2.7.egg-info/* \
"

include ${PYTHON_PN}-package-split.inc
