FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
            file://pseudo-glibc-rtld-next-workaround.patch \
           "
