SUMMARY = "circuits is a Lightweight Event driven and Asynchronous Application Framework for the Python Programming Language with a strong Component Architecture."
HOMEPAGE = "http://bitbucket.org/prologic/circuits"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0ec3fb8507687392bdc4b37c93697d04"

PR = "r3"

SRC_URI = "https://bitbucket.org/prologic/circuits/get/42e62ab9f8dd.zip"

S = "${WORKDIR}/prologic-circuits-42e62ab9f8dd"

inherit setuptools

SRC_URI[md5sum] = "918a4afe4cd30183695359da7bd6d819"
SRC_URI[sha256sum] = "c59e5bc43e52475035c0de34a26ab413b29108e527a21653551cdb251ac926b0"

include python-package-split.inc
