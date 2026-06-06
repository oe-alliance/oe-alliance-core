# package is machine specific
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI += " \
    file://0002-ffmpeg-5.patch \
    file://chromium/0002-Replace-hbbtv-responses-with-application-xhtml-xml.patch;patchdir=src/3rdparty \
    file://chromium/0024-ffmpeg7-upgrade.patch;patchdir=src/3rdparty \
    file://chromium/0025-ffmpeg_common.cc-ffmpeg8.patch;patchdir=src/3rdparty \
"
SRC_URI:append:osmio4k = " \
    file://chromium/0001-Add-initial-support-for-V4L2-mem2mem-decoder.patch;patchdir=src/3rdparty \
"
SRC_URI:append:osmio4kplus = " \
    file://chromium/0001-Add-initial-support-for-V4L2-mem2mem-decoder.patch;patchdir=src/3rdparty \
"

DEPENDS += " \
    avahi-libnss-mdns \
    libxkbcommon \
    libwebp-native \
    libxdamage \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/qtwebengine-git:"

PACKAGECONFIG[alsa] = "-feature-webengine-alsa,-no-feature-webengine-alsa,alsa-lib"
PACKAGECONFIG[extensions] = "-feature-webengine-extensions,-no-feature-webengine-extensions"

PACKAGECONFIG:append = " libwebp ffmpeg opus libvpx alsa proprietary-codecs pepper-plugins webrtc"

# Fix seccomp vs glibc >= 2.31
python do_patch:append () {
    import os
    sec_file = d.expand("${S}/src/3rdparty/chromium/sandbox/linux/system_headers/linux_seccomp.h")
    if os.path.exists(sec_file):
        with open(sec_file, "r") as f:
            lines = f.readlines()
        with open(sec_file, "w") as f:
            for line in lines:
                if "#define SYS_SECCOMP" not in line:
                    f.write(line)
}

INSANE_SKIP:${PN} += "file-rdeps"
