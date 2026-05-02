# Prevent errors like:
#   error: 'maxsize' may be used uninitialized in this function
# which are false positives?
CFLAGS:append = " -Wno-maybe-uninitialized -Wno-uninitialized"
EXTRA_OEMAKE += "WARN_CFLAGS='-Wno-discarded-qualifiers'"
PACKAGE_NO_LOCALE = "1"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://openssl40-opaque-asn1.patch"
