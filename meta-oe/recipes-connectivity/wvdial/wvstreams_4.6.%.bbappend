FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# OpenSSL 4.0 removed the APIs this uses; see the patch header.
SRC_URI += "file://openssl40-compat.patch"

DEPENDS:append = " virtual/crypt"
# configure takes boost only as the alternative to TR1, which the toolchain has.
DEPENDS:remove = "boost"
RDEPENDS:libwvstreams-base += "libxcrypt-compat"

# The recipe passes these to the C compiler only; wvstreams is C++.
CXXFLAGS:append = " -fno-strict-aliasing -fno-tree-dce -fno-optimize-sibling-calls -Wstrict-aliasing"

# Only wvtestrun needs perl, so ship it separately instead of pulling the
# interpreter in with the library.
PACKAGES =+ "${PN}-testrun"
FILES:${PN}-testrun = "${bindir}/wvtestrun"
RDEPENDS:${PN}:remove = "perl"
RDEPENDS:${PN}-testrun = "${PN} perl"
