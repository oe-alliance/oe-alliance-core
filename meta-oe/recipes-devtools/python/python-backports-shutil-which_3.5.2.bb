SUMMARY = "Backport of shutil.which from Python 3.3"
LICENSE = "PSF | MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=80c6f0d772065347b8590c8024419b97"

inherit pypi setuptools

PYPI_PACKAGE = "backports.shutil_which"

PACKAGES = "${PN}"

SRC_URI[md5sum] = "739bd7d6d03fc36ffd656e5d9c7507a5"
SRC_URI[sha256sum] = "fe39f567cbe4fad89e8ac4dbeb23f87ef80f7fe8e829669d0221ecdb0437c133"

do_install_append() {
    # python-lzma already provides __init__.py(o) files
    rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/backports/__init__.py*
}
