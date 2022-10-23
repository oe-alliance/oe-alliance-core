# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI += " \
    file://0002-ffmpeg-5.patch \
    file://chromium/0002-Replace-hbbtv-responses-with-application-xhtml-xml.patch;patchdir=src/3rdparty \
"
SRC_URI:append:osmio4k = " \
    file://chromium/0001-Add-initial-support-for-V4L2-mem2mem-decoder.patch;patchdir=src/3rdparty \
"
SRC_URI:append:osmio4kplus = " \
    file://chromium/0001-Add-initial-support-for-V4L2-mem2mem-decoder.patch;patchdir=src/3rdparty \
"

DEPENDS += " \
    libnss-mdns \
    libxkbcommon \
    libwebp-native \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/qtwebengine-git:"

PACKAGECONFIG[alsa] = "-feature-webengine-alsa,-no-feature-webengine-alsa,alsa-lib"
PACKAGECONFIG[extensions] = "-feature-webengine-extensions,-no-feature-webengine-extensions"

PACKAGECONFIG:append = " libwebp ffmpeg opus libvpx alsa proprietary-codecs pepper-plugins webrtc"

INSANE_SKIP:${PN} += "file-rdeps"
