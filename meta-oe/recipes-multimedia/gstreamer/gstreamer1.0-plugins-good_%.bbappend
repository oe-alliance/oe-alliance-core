FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
PR = "r2"

SRC_URI:append = " \
           file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
           file://0002-Revert-souphttpsrc-Always-use-the-content-decoder.patch \
"

PACKAGECONFIG_SOUP = "soup2"
RDEPENDS:${PN}-soup += "libsoup-2.4"

PACKAGECONFIG:append = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'novp9', '', 'vpx',d)} \
    wavpack gudev \
"

PV = "1.22.9"

SRC_URI[md5sum] = "4c1e66bb936b96b013ffd59fc173d006"
SRC_URI[sha256sum] = "26959fcfebfff637d4ea08ef40316baf31b61bb7729820b0684e800c3a1478b6"
