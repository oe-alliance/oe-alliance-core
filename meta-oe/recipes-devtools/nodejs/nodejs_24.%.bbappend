FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://openssl40-opaque-asn1.patch"

# ARMv7/NEON fix
CFLAGS:append:arm = " -flax-vector-conversions"
CXXFLAGS:append:arm = " -flax-vector-conversions"

# Suppress V8 C++20 template-id-cdtor and OpenSSL 3.0 deprecation warnings
CXXFLAGS:append = " -Wno-template-id-cdtor -Wno-deprecated-declarations"
