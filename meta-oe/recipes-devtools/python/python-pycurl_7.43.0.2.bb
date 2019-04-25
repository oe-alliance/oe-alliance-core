SUMMARY = "Python bindings for libcurl"
HOMEPAGE = "http://pycurl.io"
SECTION = "devel/python"
LICENSE = "LGPLv2.1+ | MIT"
LIC_FILES_CHKSUM = "file://README.rst;beginline=166;endline=182;md5=12700bdc528323033281ab039a62eddf \
                    file://COPYING-LGPL;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING-MIT;md5=7ba8b7264d9ea6da77feda54688adbd7"

DEPENDS = "curl python"
RDEPENDS_${PN} = "python-core curl"

SRC_URI = "\
        https://files.pythonhosted.org/packages/e8/e4/0dbb8735407189f00b33d84122b9be52c790c7c3b25286826f4e1bdb7bde/pycurl-${PV}.tar.gz \
        file://no-static-link.patch \
"

SRC_URI[md5sum] = "89311d1b3bb42a5dbb88609fdbdee6de"
SRC_URI[sha256sum] = "0f0cdfc7a92d4f2a5c44226162434e34f7d6967d3af416a6f1448649c09a25a4"

S = "${WORKDIR}/pycurl-${PV}"

inherit distutils

BBCLASSEXTEND = "native"

# Ensure the docstrings are generated as make clean will remove them
do_compile_prepend() {
    ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} setup.py docstrings
}

do_install_append() {
    rm -rf ${D}${datadir}/share
}

include python-package-split.inc
