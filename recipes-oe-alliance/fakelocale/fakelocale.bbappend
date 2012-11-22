FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "3"

SRC_URI = "file://lctimelocales.tar.gz file://locale.alias"

LANGUAGES += " en_GB en_US nb_NO "

RPROVIDES_${PN} += " virtual-locale-nb"
