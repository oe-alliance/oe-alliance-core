require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI[md5sum] = "7133f5a2086a7d7ef97abac610c094f5"
SRC_URI[sha256sum] = "355df2085626cdf0083c4bc0fe3017419034b6db5cce6f437ae8234a5e90b40c"

SRC_URI += " \
        file://dvb_frontend-Multistream-support-3.3.patch \
"