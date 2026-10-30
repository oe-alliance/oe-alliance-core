FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"

SRC_URI:append = " \
           file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
           file://0002-Revert-souphttpsrc-Always-use-the-content-decoder.patch \
           file://0003-qtdemux-preserve-adaptive-track-title.patch \
           file://0004-hlsdemux2-exact-segment-time.patch \
           file://0005-matroskademux-avs2.patch \
           file://0006-qtdemux-avs.patch \
           file://0007-aacparse-wait-for-the-rest-of-a-split-frame.patch \
"

PACKAGECONFIG:remove = "soup3"

RDEPENDS:${PN}-soup:remove = "${MLPREFIX}libsoup"
RDEPENDS:${PN}-soup += "${MLPREFIX}${@bb.utils.contains('PACKAGECONFIG', 'soup2', 'libsoup-2.4', 'libsoup', d)}"

PACKAGECONFIG[soup2] = "-Dsoup=enabled,-Dsoup=disabled,libsoup-2.4"

PACKAGECONFIG:append = " \
    soup2 \
    ${@bb.utils.contains('MACHINE_FEATURES', 'novp9', '', 'vpx',d)} \
    wavpack gudev amrnb amrwb \
"

PACKAGECONFIG_CONFARGS:remove = "-Dsoup=disabled"
EXTRA_OEMESON:append = " -Dsoup=enabled"
