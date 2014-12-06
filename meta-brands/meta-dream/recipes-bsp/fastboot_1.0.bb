SUMMARY = "Fastboot stuff for a Dreambox"

SRC_URI[dm820.md5sum] = "8b679de32fd019dd18278aec08054041"
SRC_URI[dm820.sha256sum] = "7eafd3ede52267a7f2701884e1f4bfb4717493fe4674d54aa6a66da626cd618f"

inherit opendreambox-precompiled-binary2

PACKAGE_ARCH = "${MACHINE}"

COMPATIBLE_MACHINE = "^(dm820)$"
