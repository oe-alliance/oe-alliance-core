SUMMARY = "Dreambox TPM Daemon"
DEPENDS = "openssl"

SRC_URI[armv7ahf-neon.md5sum] = "b4ec8731a9a255f13679560a76487909"
SRC_URI[armv7ahf-neon.sha256sum] = "3c29413fec80777e26b07e337129fc558361302a0c08af7ccfa9db6e69a8bda0"
SRC_URI[cortexa15hf-neon-vfpv4.md5sum] = "25abf48399b57e291b3a587b701954c6"
SRC_URI[cortexa15hf-neon-vfpv4.sha256sum] = "b1b81856a411311ffca0e391da473bdd00d23b2e6503845c81e4a0f65ec0eb87"
SRC_URI[mips32el.md5sum] = "93038ebc0d9bd4b0b41c0de8380af099"
SRC_URI[mips32el.sha256sum] = "fa42e5c365b091d75617fa8e0fe47e21c0acc3ca46426f7a1d2bc5e1a58279f2"

inherit opendreambox-precompiled-binary3 update-rc.d

INITSCRIPT_NAME = "${PN}"
INHIBIT_PACKAGE_STRIP = "1"
INITSCRIPT_PARAMS = "start 60 S ."
