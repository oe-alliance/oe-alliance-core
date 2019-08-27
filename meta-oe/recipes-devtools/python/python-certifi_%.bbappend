do_install_append() {
	perm_files=`find "${D}${PYTHON_SITEPACKAGES_DIR}/" -name "top_level.txt"`
	for f in $perm_files; do
		chmod 644 "${f}"
	done

	# certifi ships its own duplicate of /etc/ssl/certs/ca-certificates.crt
	# Strip and link to the real thing instead
	rm ${D}${PYTHON_SITEPACKAGES_DIR}/certifi/cacert.pem
	ln -s /etc/ssl/certs/ca-certificates.crt ${D}${PYTHON_SITEPACKAGES_DIR}/certifi/cacert.pem
}

include python-package-split.inc
