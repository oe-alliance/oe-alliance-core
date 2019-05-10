DESCRIPTION = "A comprehensive HTTP client library"
HOMEPAGE = "https://github.com/httplib2/httplib2"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=ad87b270277b5f40e2a281d9f7dde584"

RDEPENDS_${PN} = "ca-certificates"

SRC_URI = "https://files.pythonhosted.org/packages/67/33/29779c5aaeac796679a37bf798b3c2adbfaae7dbf13e966b0ab9c3aa06c0/httplib2-${PV}.tar.gz"

SRC_URI[md5sum] = "f78ce222216865271f2672e6941174d5"
SRC_URI[sha256sum] = "a18121c7c72a56689efbf1aef990139ad940fee1e64c6f2458831736cd593600"

S = "${WORKDIR}/httplib2-${PV}"

inherit setuptools python-dir

do_compile_append() {
       python -O -m compileall ${W}/build
}

do_install_append() {
    perm_files=`find "${D}${PYTHON_SITEPACKAGES_DIR}/" -name "top_level.txt"`
    for f in $perm_files; do
        chmod 644 "${f}"
    done

    # httplib2 ships its own (outdated?) duplicate of /etc/ssl/certs/ca-certificates.crt
    # Strip and link to the real thing instead
    rm ${D}${PYTHON_SITEPACKAGES_DIR}/httplib2/cacerts.txt
    ln -s /etc/ssl/certs/ca-certificates.crt ${D}${PYTHON_SITEPACKAGES_DIR}/httplib2/cacerts.txt
}

include python-package-split.inc
