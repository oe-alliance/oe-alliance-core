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

PV = "1.22.5"

SRC_URI[md5sum] = "8c5a67f702ff5f30290be35ac27b3ac9"
SRC_URI[sha256sum] = "b67b31313a54c6929b82969d41d3cfdf2f58db573fb5f491e6bba5d84aea0778"
