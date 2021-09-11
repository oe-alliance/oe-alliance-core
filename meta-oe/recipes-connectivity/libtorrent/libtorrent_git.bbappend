CXXFLAGS_append_sh4 += " -std=c++11 -fPIC -fno-strict-aliasing "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = "\
    file://0001-Define-64bit-atomic-helpers-for-sh4.patch \
    file://fix-build-openssl102q.patch \
"

