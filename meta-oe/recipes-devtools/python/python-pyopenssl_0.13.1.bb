SUMMARY = "Simple Python wrapper around the OpenSSL library"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRCNAME = "pyOpenSSL"

DEPENDS = "openssl"
RDEPENDS_${PN} = "python-threading"

PR = "r5"

SRC_URI = "http://pypi.python.org/packages/source/p/pyOpenSSL/pyOpenSSL-${PV}.tar.gz \
           file://disable_test_set_default_verify_paths.patch \
           file://bts733366_make_symbol_optional.patch \
           file://0001-crl-fix.patch \
"
SRC_URI[md5sum] = "e27a3b76734c39ea03952ca94cc56715"
SRC_URI[sha256sum] = "ba06ec710414f6dfe5566ec24c81882547c3e6fc48458d64315b73a0d5142fdb"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils

include python-package-split.inc

