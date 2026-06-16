FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"

SRC_URI:append = " \
           file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
           file://0002-Revert-souphttpsrc-Always-use-the-content-decoder.patch \
"

PACKAGECONFIG:remove = "soup3"
RDEPENDS:${PN}-soup += "libsoup-2.4"

PACKAGECONFIG[soup2] = "-Dsoup=enabled,-Dsoup=disabled,libsoup-2.4"

PACKAGECONFIG:append = " \
    soup2 \
    ${@bb.utils.contains('MACHINE_FEATURES', 'novp9', '', 'vpx',d)} \
    wavpack gudev amrnb amrwb \
"

PACKAGECONFIG_CONFARGS:remove = "-Dsoup=disabled"
EXTRA_OEMESON:append = " -Dsoup=enabled"

PV = "1.28.4"
 
SRC_URI[sha256sum] = "c825ea737c59cea0e4a0c41da2388045ff5dd32d162220ac93a7a82ee4a04e61"
