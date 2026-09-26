FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

include python3-package-split.inc

DEPENDS += " libwebp libavif "

PEP517_BUILD_OPTS = " \
    -C platform-guessing=disable \
    -C zlib=enable \
    -C jpeg=enable \
    -C tiff=enable \
    -C freetype=enable \
    -C lcms=enable \
    -C jpeg2000=enable \
    -C webp=enable \
    -C webpmux=enable \
    -C avif=enable \
    -C imagequant=disable \
"

PR .= ".3"
