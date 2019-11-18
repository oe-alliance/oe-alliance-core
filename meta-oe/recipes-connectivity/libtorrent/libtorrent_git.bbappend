FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://fix-build-openssl102q.patch"

# disable-instrumentation to fix atomics errors
PACKAGECONFIG = ""
