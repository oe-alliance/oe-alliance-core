SUMMARY = "Dreambox TS/M2TS audio passthrough helper lib"
DEPENDS = "libdlsym openssl"

SRC_URI[cortexa15hf-neon-vfpv4.md5sum] = "6866fe0be3f76a3a5b9ee1319c6456d3"
SRC_URI[cortexa15hf-neon-vfpv4.sha256sum] = "da01a9c898f1fe961822ce3ee1b49917cad190788cd54aea8880f4bffa4964f9"
SRC_URI[mips32el.md5sum] = "d3062d2d97f2086efb8e5e2f09a3608b"
SRC_URI[mips32el.sha256sum] = "37096521367538d7edc46313adde6ab7b9a4e9ca86f5469099183bc96ed921ce"

inherit opendreambox-precompiled-binary3

FILES_${PN} = "${libdir}/lib*${SOLIBSDEV}"
FILES_SOLIBSDEV = ""

DEBIAN_NOAUTONAME_${PN} = "1"
