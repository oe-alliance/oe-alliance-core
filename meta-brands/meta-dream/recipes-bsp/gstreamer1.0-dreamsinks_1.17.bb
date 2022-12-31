SUMMARY = "dreambox video and audio sink elements for Gstreamer 1.0"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base openssl"

SRC_URI[aarch64.md5sum] = "8d628817f28ec1af8230fb50c6d2766a"
SRC_URI[aarch64.sha256sum] = "933690b758e0b0c4b56b0882b42f8fe5e8bf9075562f88762144a28c269f9157"

inherit opendreambox-precompiled-binary4

FILES:${PN} = "${libdir}/gstreamer-1.0/*.so"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

INSANE_SKIP:${PN} = "file-rdeps"
