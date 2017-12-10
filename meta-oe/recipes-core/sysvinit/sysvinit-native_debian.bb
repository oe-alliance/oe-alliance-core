require sysvinit.inc

PR = "${INC_PR}.0"

inherit native

# fix-makefile-native.patch: patch file remove changing of permission in native-package.
SRC_URI += "file://fix-makefile-native.patch"
