SUMMARY = "OE-Alliance Base Image - Enigma2 without distro skin"

require oea-image-common.inc
require oea-image-enigma2.inc

export IMAGE_BASENAME = "oe-alliance-base-image"
IMAGE_NAME = "oe-alliance-base-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE}"
IMAGE_NAME[vardepsexclude] += "DATE"
