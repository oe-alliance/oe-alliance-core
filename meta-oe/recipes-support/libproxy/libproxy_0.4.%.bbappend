FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:sh4 = " file://0001-include-errno-header.patch"

CXXFLAGS:append:sh4 = " -std=c++11"
