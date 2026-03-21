SUMMARY = "OE-Alliance Shell Image - minimal bootable Linux with shell"

require oea-image-common.inc
require oea-image-minimal.inc

export IMAGE_BASENAME = "oe-alliance-shell-image"
IMAGE_NAME = "oe-alliance-shell-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE}"
IMAGE_NAME[vardepsexclude] += "DATE"
