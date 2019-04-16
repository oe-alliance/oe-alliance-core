FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://0001-mesonbuild-Recognise-sh4-architecture.patch \
"
