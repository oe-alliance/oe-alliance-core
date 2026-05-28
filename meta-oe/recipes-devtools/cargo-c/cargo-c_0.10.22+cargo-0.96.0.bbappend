FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# vendored curl 8.15.0 inside curl-sys 0.4.83 cannot build against
# OpenSSL 4.0 (ASN1_INTEGER / ASN1_BIT_STRING became opaque)
SRC_URI += "file://0001-curl-sys-openssl4-asn1-accessors.patch;patchdir=../curl-sys-0.4.83+curl-8.15.0/"
