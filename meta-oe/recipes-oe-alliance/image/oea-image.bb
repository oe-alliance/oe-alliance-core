SUMMARY = "OE-Alliance Full Image (Enigma2 + Feed)"

DEPENDS += "packagegroup-oea-feed-${DISTRO}"

require oea-image-distro.inc

export IMAGE_BASENAME = "${DISTRO}-image"
IMAGE_NAME = "${DISTRO_NAME}-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE}"
IMAGE_NAME[vardepsexclude] += "DATE"
