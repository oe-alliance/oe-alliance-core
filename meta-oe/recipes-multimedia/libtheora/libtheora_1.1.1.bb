require libtheora.inc

PR = "${INC_PR}.1"
 
SRC_URI += "file://no-docs.patch;patch=1"

PROVIDES =+ " libtheora0"
PACKAGES =+ " libtheora0"

