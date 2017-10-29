SUMMARY = "Dreambox TPM Daemon"
DEPENDS = "openssl"

SRC_URI[cortexa15hf-neon-vfpv4.md5sum] = "95a807b5972ba0eecdbec1c24d99a872"
SRC_URI[cortexa15hf-neon-vfpv4.sha256sum] = "a7817b4fbce1847e3975d8dd3f725c284d03d97840c5c83c140ef84e78ba1c82"
SRC_URI[mips32el.md5sum] = "93038ebc0d9bd4b0b41c0de8380af099"
SRC_URI[mips32el.sha256sum] = "fa42e5c365b091d75617fa8e0fe47e21c0acc3ca46426f7a1d2bc5e1a58279f2"

inherit opendreambox-precompiled-binary3 update-rc.d

INITSCRIPT_NAME = "${PN}"
INHIBIT_PACKAGE_STRIP = "1"
INITSCRIPT_PARAMS = "start 60 S ."

INSANE_SKIP_${PN} += "file-rdeps"
