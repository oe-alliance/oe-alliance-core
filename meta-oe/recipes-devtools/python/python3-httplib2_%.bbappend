RDEPENDS:${PN} += "ca-certificates"

inherit python3-dir

do_install:append() {
    perm_files=`find "${D}${PYTHON_SITEPACKAGES_DIR}/" -name "top_level.txt"`
    for f in $perm_files; do
        chmod 644 "${f}"
    done

    # httplib2 ships its own (outdated?) duplicate of /etc/ssl/certs/ca-certificates.crt
    # Strip and link to the real thing instead
    rm ${D}${PYTHON_SITEPACKAGES_DIR}/httplib2/cacerts.txt
    ln -s /etc/ssl/certs/ca-certificates.crt ${D}${PYTHON_SITEPACKAGES_DIR}/httplib2/cacerts.txt
}

include python3-package-split.inc
