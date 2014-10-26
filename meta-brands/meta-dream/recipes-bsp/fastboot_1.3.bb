SUMMARY = "Fastboot stuff for a Dreambox"

SRC_URI[dm7080.md5sum] = "25e230579143dbb1c35e68955d1c88e5"
SRC_URI[dm7080.sha256sum] = "fb19e6b5e8f94c5c637e324f0dd367304c2b88f5faf4ca1bcb6b537014d98623"

inherit opendreambox-precompiled-binary2

PACKAGE_ARCH = "${MACHINE}"

COMPATIBLE_MACHINE = "^(dm7080)$"
