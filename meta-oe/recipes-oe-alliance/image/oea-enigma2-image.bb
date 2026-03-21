SUMMARY = "OE-Alliance Enigma2 Image without Feed"

require oea-image-distro.inc

export IMAGE_BASENAME = "${DISTRO}-image"
IMAGE_NAME = "${DISTRO_NAME}-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE}"
IMAGE_NAME[vardepsexclude] += "DATE"
