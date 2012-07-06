FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "2"

SRC_URI = "file://lctimelocales.tar.gz file://locale.alias"

LANGUAGES += " en_GB_GB"

RPROVIDES_${PN} += " virtual-locale-en_GB"
