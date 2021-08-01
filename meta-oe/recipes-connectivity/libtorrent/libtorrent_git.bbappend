FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " file://fix-build-openssl102q.patch"

# disable-instrumentation to fix atomics errors
PACKAGECONFIG = ""
