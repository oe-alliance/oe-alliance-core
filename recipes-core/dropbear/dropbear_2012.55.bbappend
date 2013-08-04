# Disable DNS lookups which are pointless and slow down logins

PRINC := "${@int(PRINC) + 1}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://disable-reverse-dns-lookups.patch"
