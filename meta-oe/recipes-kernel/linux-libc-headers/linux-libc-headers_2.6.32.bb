require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI[md5sum] = "f9c782399f1609c67bb78405a8d6e021"
SRC_URI[sha256sum] = "723106c298c5ba8733bb51ecc106b05e332a18490edc5f61cc6e57dbb36dcc46"

SRC_URI += "\
    file://linuxdvb.patch \
    file://ppp.patch \
    file://types.patch \
    file://taskstats.patch \
"
