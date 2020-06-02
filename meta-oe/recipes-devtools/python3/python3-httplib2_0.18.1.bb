DESCRIPTION = "A comprehensive HTTP client library"
HOMEPAGE = "https://github.com/httplib2/httplib2"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=7e04d1303d64a7b62421751ecb490dc2"

RDEPENDS_${PN} = "ca-certificates"

SRC_URI = "https://files.pythonhosted.org/packages/98/3f/0769a851fbb0ecc458260055da67d550d3015ebe6b8b861c79ad00147bb9/httplib2-0.18.1.tar.gz"
SRC_URI[md5sum] = "0b331f96cdb2ae0e0342d4ea0f5f0502"
SRC_URI[sha256sum] = "8af66c1c52c7ffe1aa5dc4bcd7c769885254b0756e6e69f953c7f0ab49a70ba3"

S = "${WORKDIR}/httplib2-${PV}"

inherit setuptools3 ${PYTHON_PN}-dir

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

include ${PYTHON_PN}-package-split.inc
