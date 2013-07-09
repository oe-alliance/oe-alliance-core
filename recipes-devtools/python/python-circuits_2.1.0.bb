DESCRIPTION = "circuits is a Lightweight Event driven and Asynchronous Application Framework for the Python Programming Language with a strong Component Architecture."
HOMEPAGE = "http://bitbucket.org/prologic/circuits"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0ec3fb8507687392bdc4b37c93697d04"

PR = "r2"

SRC_URI = "https://bitbucket.org/prologic/circuits/get/42e62ab9f8dd.zip"

S = "${WORKDIR}/prologic-circuits-42e62ab9f8dd"

inherit distutils

SRC_URI[md5sum] = "94d9df26a33cb610db4cb45204ce6fa9"
SRC_URI[sha256sum] = "445e1ccae7aa8ab0dd3b8d629597f099c8c24fe2e2117b8be0f465f7dfabf854"

FILES_${PN}-tests = " \
  ${libdir}/${PYTHON_DIR}/site-packages/*/tests \
  ${libdir}/${PYTHON_DIR}/site-packages/*/*/tests \
"

PACKAGES =+ "${PN}-tests"
