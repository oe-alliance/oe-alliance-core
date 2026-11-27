FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

include python3-package-split.inc

# The target builds support AVIF. The OE layer has no native libavif provider.
PILLOW_AVIF = "enable"
PILLOW_AVIF:class-native = "disable"
DEPENDS += "libwebp ${@'libavif' if d.getVar('PILLOW_AVIF') == 'enable' else ''}"

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
    -C avif=${PILLOW_AVIF} \
    -C imagequant=disable \
"

PR .= ".3"
