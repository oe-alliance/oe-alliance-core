SUMMARY = "circuits is a Lightweight Event driven and Asynchronous Application Framework for the Python Programming Language with a strong Component Architecture."
HOMEPAGE = "http://bitbucket.org/prologic/circuits"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0ec3fb8507687392bdc4b37c93697d04"


SRC_URI = "https://bitbucket.org/prologic/circuits/get/42e62ab9f8dd.zip"

S = "${WORKDIR}/prologic-circuits-42e62ab9f8dd"

inherit setuptools

SRC_URI[md5sum] = "35b09153c13eb5151f219b9857328c96"
SRC_URI[sha256sum] = "bddf798d44cc3b149c187eea1df6c78df90de75a873f6401aadc45c2d5ecedea"

include python-package-split.inc

do_populate_sysroot[noexec] = "1"
