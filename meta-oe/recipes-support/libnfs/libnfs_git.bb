SUMMARY = "NFS client library"

LICENSE = "GPL-3.0-only AND LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=825301ba17efc9d188ee0abd4b924ada"

PV = "16.2.0+git"
SRCREV = "20971deebdf0e0468521bf5b892c4154fc85ec42"

SRC_URI = "git://github.com/sahlberg/libnfs.git;protocol=https;branch=master"

inherit cmake

EXTRA_OECMAKE = "-DBASE_LIB_PATH=${baselib}  -DCMAKE_POLICY_VERSION_MINIMUM=3.5"

# The upstream CMake export embeds ${libdir} as an absolute target path.  It is
# not relocatable into an OpenEmbedded recipe sysroot, while libnfs.pc is.
# Let consumers (including Kodi) discover libnfs through pkg-config instead.
do_install:append() {
    rm -rf ${D}${libdir}/cmake/libnfs
}
