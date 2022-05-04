FILESEXTRAPATHS:prepend:sh4 := "${THISDIR}/boost:"

SRC_URI:append:sh4 = " file://support-sh4-arch.patch"

CXXFLAGS:append:sh4 = " -std=c++11"
