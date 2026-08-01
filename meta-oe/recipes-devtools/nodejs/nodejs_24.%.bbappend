FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://gyp-add-link-settings-to-non-config-keys.patch"

# ARMv7/NEON fix
CFLAGS:append:arm = " -flax-vector-conversions"
CXXFLAGS:append:arm = " -flax-vector-conversions"

# Suppress V8 / GCC 16 / OpenSSL 3 warnings that become hard errors
CXXFLAGS:append = " -Wno-template-id-cdtor -Wno-template-body -Wno-deprecated-declarations"
