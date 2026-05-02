FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-build-openssl102q.patch file://openssl40-opaque-asn1.patch"
