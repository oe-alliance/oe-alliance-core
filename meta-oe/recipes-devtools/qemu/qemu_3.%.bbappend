FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "file://linux-user-fix-to-handle-variably-sized-SIOCGSTAMP-w.patch \
                file://rename-gettid-to-sys_gettid-to-avoid-clash-with-glibc.patch"

CFLAGS_append = " -Wno-address-of-packed-member -Wno-stringop-truncation"
