FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PV = "20250127.1"
SRCREV = "d9e4955c65cd4367dd6bf46f4ccb8cd3d100540b"
BRANCH = "lts_2025_01_27"

SRC_URI:remove = "file://0002-abseil-ppc-fixes.patch"

SRC_URI += "file://0002-Remove-maes-option-from-cross-compilation.patch \
           file://0003-Remove-neon-option-from-cross-compilation.patch \
           file://0004-abseil-ppc-fixes.patch \
           file://0005-Fix-GCC15-warning-that-ciso646-is-deprecated-in-C-17.patch \
           "
