FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"

SRC_URI:append = " \
           file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
           file://0002-Revert-souphttpsrc-Always-use-the-content-decoder.patch \
"

PACKAGECONFIG:remove = "soup3"
PACKAGECONFIG_SOUP = "soup2"
RDEPENDS:${PN}-soup += "libsoup-2.4"

PACKAGECONFIG[soup2]      = "-Dsoup=enabled,,libsoup-2.4,,,soup3"
PACKAGECONFIG[soup3]      = "-Dsoup=enabled,,libsoup,,,soup2"
PACKAGECONFIG[soup3]      = "-Dsoup=enabled,-Dsoup=disabled,libsoup"

PACKAGECONFIG:append = " \
    ${PACKAGECONFIG_SOUP} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'novp9', '', 'vpx',d)} \
    wavpack gudev amrnb amrwb \
"

PV = "1.28.1"
SRC_URI[sha256sum] = "738e26aee41b7a62050e40b81adc017a110a7f32d1ec49fa6a0300846c44368d"
