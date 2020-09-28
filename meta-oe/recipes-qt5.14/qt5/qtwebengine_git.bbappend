# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI += " \
    file://0001-revert-robustness.patch \
    file://0002-set-useragent.patch \
    file://0003-detect-ninja-version.patch \
    file://chromium/0001-mpris-for-linux.patch;patchdir=src/3rdparty \
    file://chromium/0002-widevine-for-linux.patch;patchdir=src/3rdparty \
    file://chromium/0003-set-useragent.patch;patchdir=src/3rdparty \
    file://chromium/0006-fix-build-with-bison-3.7.patch;patchdir=src/3rdparty \
    file://chromium/0007-fix-build-with-gcc-10.patch;patchdir=src/3rdparty \
"

DEPENDS += " \
    libnss-mdns \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtwebengine-git:"

PACKAGECONFIG_append = " ffmpeg opus libvpx proprietary-codecs pepper-plugins webrtc"

INSANE_SKIP_${PN} += "file-rdeps"
