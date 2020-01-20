DESCRIPTION = "A comprehensive HTTP client library"
HOMEPAGE = "https://github.com/httplib2/httplib2"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=5c5e308e92d0fb353d624de5faf34be3"

RDEPENDS_${PN} = "ca-certificates"

SRC_URI = "https://files.pythonhosted.org/packages/54/3e/ec53cf9e084a158a51bc669c40facfe5c5c93d194ca8fdaf4c933ea05d77/httplib2-${PV}.tar.gz"
SRC_URI[md5sum] = "f7f76d00b275593c4521585858041653"
SRC_URI[sha256sum] = "b6a5a3faa31b56d6eaa61b22e328bfa73a877fe4308b02aa98d6d424ff865564"

S = "${WORKDIR}/httplib2-${PV}"

inherit setuptools python-dir

do_compile_append() {
       python2 -O -m compileall ${W}/build
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
