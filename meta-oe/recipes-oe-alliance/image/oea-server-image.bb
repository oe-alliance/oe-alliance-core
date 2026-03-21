SUMMARY = "OE-Alliance Server Image - headless STB with networking"

require oea-image-common.inc
require oea-image-headless.inc

export IMAGE_BASENAME = "oe-alliance-server-image"
IMAGE_NAME = "oe-alliance-server-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE}"
IMAGE_NAME[vardepsexclude] += "DATE"
