SUMMARY = "Dreambox TS/M2TS audio passthrough helper lib"
DEPENDS = "libdlsym openssl"

SRC_URI[cortexa15hf-neon-vfpv4.md5sum] = "2760da32a2553c88f6782525bb986495"
SRC_URI[cortexa15hf-neon-vfpv4.sha256sum] = "498c3412025350da77f67bd56bcc32a60c5692a164b8605f090029029ea1a2b7"
SRC_URI[mips32el.md5sum] = "c0cb3a7d196acdeeb9717ee837268c84"
SRC_URI[mips32el.sha256sum] = "2a13e4eb92d6b8ad28d4cb8af5e39c1507280dcb9631d1ae3ec0494c7d05c021"

inherit opendreambox-precompiled-binary3

FILES_${PN} = "${libdir}/lib*${SOLIBSDEV}"
FILES_SOLIBSDEV = ""

DEBIAN_NOAUTONAME_${PN} = "1"

INSANE_SKIP_${PN} += "file-rdeps"
