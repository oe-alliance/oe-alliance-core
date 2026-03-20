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

PV = "1.28.1"
SRC_URI[sha256sum] = "738e26aee41b7a62050e40b81adc017a110a7f32d1ec49fa6a0300846c44368d"
