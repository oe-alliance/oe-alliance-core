SUMMARY  = "JPyExecJS is a porting of ExecJS from Ruby. PyExecJS automatically picks the best runtime available to evaluate your JavaScript program."
DESCRIPTION = "Run JavaScript code from Python"
HOMEPAGE = "https://pypi.org/project/PyExecJS"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=70f9df77ea55ba7d1f19e18f62cf5bb6"

# This library is no longer maintananced. Bugs are not be fixed (even if they are trivial or essential).
# We suggest to use other library or to make a fork.

SRC_URI = "https://files.pythonhosted.org/packages/ba/8e/aedef81641c8dca6fd0fb7294de5bed9c45f3397d67fddf755c1042c2642/PyExecJS-${PV}.tar.gz"

S = "${WORKDIR}/PyExecJS-${PV}"

inherit setuptools3

SRC_URI[md5sum] = "f530b8e14373714448a94f458d24d1d6"
SRC_URI[sha256sum] = "34cc1d070976918183ff7bdc0ad71f8157a891c92708c00c5fbbff7a769f505c"

include ${PYTHON_PN}-package-split.inc
