SUMMARY  = "JPyExecJS is a porting of ExecJS from Ruby. PyExecJS automatically picks the best runtime available to evaluate your JavaScript program."
DESCRIPTION = "Run JavaScript code from Python"
HOMEPAGE = "https://pypi.python.org/pypi/PyExecJS"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=70f9df77ea55ba7d1f19e18f62cf5bb6"

PR = "r0"

SRC_URI = "http://pypi.python.org/packages/b6/56/affb227598d7e42b28e7be24fe9240a84f3aa0cfd65a2abdbfdfd3d2f7c6/PyExecJS-${PV}.zip"

S = "${WORKDIR}/PyExecJS-${PV}"

inherit setuptools

SRC_URI[md5sum] = "714edb64c8914e94eb45541e123c420b"
SRC_URI[sha256sum] = "31346cdf19d1e64840f0104f8be1c1231cb9ce3de9919828419814567cc2e691"

include python-package-split.inc
