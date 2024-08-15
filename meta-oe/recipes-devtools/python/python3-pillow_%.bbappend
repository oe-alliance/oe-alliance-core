FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

include ${PYTHON_PN}-package-split.inc

DEPENDS += " libwebp "

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
    -C imagequant=disable \
"

PR .= ".2"
