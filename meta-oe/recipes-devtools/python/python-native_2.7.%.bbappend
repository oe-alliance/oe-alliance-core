MY_EXTRA_PATH := "${THISDIR}"
FILESPATH .= ":${MY_EXTRA_PATH}/python-native/"
SRC_URI += "file://04-default-is-optimized.patch"
