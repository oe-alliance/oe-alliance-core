SUMMARY = "Independent command-line DAB/DAB+ receiver and analyser"
DESCRIPTION = "welle-cli receives complete DAB ensembles from RTL-SDR hardware and provides both diagnostic output and the compressed-audio Enigma2 frontend."
HOMEPAGE = "https://www.welle.io/"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=211626514f1461d76feb728b8caf0a9c"

# Pin the diagnostic decoder so results remain comparable across image builds.
SRCREV = "512558d1f8ac4c524d3c63e97510ea36c1bd7a70"
PV = "2.7+git"
PR = "r3"

SRC_URI = "git://github.com/AlbrechtL/welle.io.git;protocol=https;branch=master \
           file://0001-mpg123-use-openembedded-largefile-api.patch \
           file://0002-log-dynamic-label-plus-groups.patch \
           file://0003-add-enigma2-compressed-audio-frontend.patch \
           file://0004-add-enigma2-frontend-source.patch \
           file://0005-add-packet-mode-spi-decoding.patch \
"

DEPENDS = "alsa-lib faad2 fftw lame libusb1 mpg123 rtl-sdr xxd-native"
RDEPENDS:${PN} = "rtl-sdr"

EXTRA_OECMAKE = "-DBUILD_WELLE_IO=OFF \
                  -DBUILD_WELLE_CLI=ON \
                  -DBUILD_WELLE_E2=ON \
                  -DRTLSDR=ON \
                  -DAIRSPY=OFF \
                  -DSOAPYSDR=OFF \
                  -DFLAC=OFF \
                  -DWITH_APP_BUNDLE=OFF \
                  -DCMAKE_POLICY_VERSION_MINIMUM=3.5"

inherit cmake pkgconfig
