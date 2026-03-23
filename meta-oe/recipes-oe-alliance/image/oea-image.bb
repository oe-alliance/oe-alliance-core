SUMMARY = "OE-Alliance Full Image (Enigma2 + Feed)"

DEPENDS += "packagegroup-oea-feed-${DISTRO}"

require oea-image-distro.inc

export IMAGE_BASENAME = "${DISTRO}-image"
