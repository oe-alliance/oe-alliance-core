PRINC = "2"

SRC_URI = "git://github.com/oe-alliance/openpli-plugins.git;protocol=git \
		   file://pythonpaths.patch"
		   
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
