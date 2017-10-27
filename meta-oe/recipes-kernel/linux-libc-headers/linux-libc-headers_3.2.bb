require recipes-kernel/linux-libc-headers/linux-libc-headers.inc


SRC_URI += " \
        file://dvb_frontend-Multistream-support-3.2.patch \
"

SRC_URI[md5sum] = "364066fa18767ec0ae5f4e4abcf9dc51"
SRC_URI[sha256sum] = "dd96ed02b53fb5d57762e4b1f573460909de472ca588f81ec6660e4a172e7ba7"
