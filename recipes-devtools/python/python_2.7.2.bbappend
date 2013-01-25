FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 1}"

SRC_URI += " \
			file://no-getaddrinfo-check.patch \
"

EXTRA_OECONF += " \
	--enable-ipv6 \
"
