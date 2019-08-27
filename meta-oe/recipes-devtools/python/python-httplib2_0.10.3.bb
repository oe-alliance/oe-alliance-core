DESCRIPTION = "A comprehensive HTTP client library"
HOMEPAGE = "https://code.google.com/p/httplib2/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=41a98bc55b04b2a38acdb5c8ab0ae6b2"

SRCNAME = "httplib2"

SRC_URI = "https://files.pythonhosted.org/packages/source/h/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
            file://0001-0.10.3_to_4caa6d080c23a1ddf9c7db789ad66743111b704d.patch;patch=1 \
"

SRC_URI[md5sum] = "709c305e1b00e9c0af49ee816429569c"
SRC_URI[sha256sum] = "e404d3b7bd86c1bc931906098e7c1305d6a3a6dcef141b8bb1059903abb3ceeb"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools python-dir

do_compile_append() {
       python -O -m compileall ${W}/build
}

do_install_append() {
	perm_files=`find "${D}${PYTHON_SITEPACKAGES_DIR}/" -name "top_level.txt"`
	for f in $perm_files; do
		chmod 644 "${f}"
	done

	# httplib2 ships its own (outdated?) duplicate of ${sysconfdir}/ssl/certs/ca-certificates.crt
	# Strip and link to the real thing instead
	rm ${D}${PYTHON_SITEPACKAGES_DIR}/httplib2/cacerts.txt
	ln -s ${sysconfdir}/ssl/certs/ca-certificates.crt ${D}${PYTHON_SITEPACKAGES_DIR}/httplib2/cacerts.txt
}

RDEPENDS_${PN} = "ca-certificates"

PACKAGES =+ " ${PN}-src"
RDEPENDS_{PN}-src = "${PN}"

FILES_${PN}-src = " \
    ${PYTHON_SITEPACKAGES_DIR}/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    "

FILES_${PN}-dbg += " \
    ${PYTHON_SITEPACKAGES_DIR}/*/.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*.egg-info \
    "
