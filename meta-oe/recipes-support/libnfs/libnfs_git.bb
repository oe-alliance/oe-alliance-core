SUMMARY = "NFS client library"

LICENSE = "GPL-3.0-only AND LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=825301ba17efc9d188ee0abd4b924ada"

PV = "18.0.0+git"
SRCREV = "9e301e90e47902e340e88a79e7fedc289fef1a66"

SRC_URI = "git://github.com/sahlberg/libnfs.git;protocol=https;branch=master"

inherit cmake

# Do not auto-detect optional security libraries from the cross-build sysroot.
PACKAGECONFIG ??= ""
PACKAGECONFIG[tls] = "-DENABLE_TLS=ON,-DENABLE_TLS=OFF,gnutls"
PACKAGECONFIG[krb5] = "-DENABLE_KRB5=ON,-DENABLE_KRB5=OFF,krb5"

# The upstream CMake export embeds ${libdir} as an absolute target path.  It is
# not relocatable into an OpenEmbedded recipe sysroot, while libnfs.pc is.
# Let consumers (including Kodi) discover libnfs through pkg-config instead.
do_install:append() {
    rm -rf ${D}${libdir}/cmake/libnfs
}
