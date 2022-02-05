SUMMARY  = "A module to work with countries and languages"
HOMEPAGE = "https://github.com/Diaoul/babelfish"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6570714fbba8ff492626c8753620c54d"

SRC_URI[md5sum] = "985464e05a7fd275d650347af8aa2439"
SRC_URI[sha256sum] = "2dadfadd1b205ca5fa5dc9fa637f5b7933160a0418684c7c46a7a664033208a2"

S = "${WORKDIR}/babelfish-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
