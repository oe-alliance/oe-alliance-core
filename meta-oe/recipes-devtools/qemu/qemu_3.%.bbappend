FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "file://linux-user-fix-to-handle-variably-sized-SIOCGSTAMP-w.patch"
