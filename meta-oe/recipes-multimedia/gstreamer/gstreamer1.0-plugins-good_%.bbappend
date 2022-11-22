FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"


PR = "r1"

SRC_URI:append = " \
           file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
"

RDEPENDS:${PN}-soup += "libsoup"

PACKAGECONFIG:append = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'novp9', '', 'vpx',d)} \
    wavpack \
"
