require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI[md5sum] = "1a1760420eac802c541a20ab51a093d1"
SRC_URI[sha256sum] = "4ab9a6ef1c1735713f9f659d67f92efa7c1dfbffb2a2ad544005b30f9791784f"


SRC_URI += " \
        file://dvb_frontend-Multistream-support-3.6.patch \
"
