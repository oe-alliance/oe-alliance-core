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
    wavpack gudev amrnb amrwb \
"

PV = "1.24.13"
 
SRC_URI[md5sum] = "9719e6a5a00e5cb3e504e60367fe7c6a"
SRC_URI[sha256sum] = "574ac6f9fd84b32eb04e80572391d6762df6f9802a47bc0386cd6cc48c14d08b"
