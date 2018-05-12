require wetek-libamcodec.inc

S = "${WORKDIR}/libamcodec-75f23da/amcodec"

SRC_URI = "file://libamcodec-75f23da.tar.gz;md5=2ff1cbc415271733e1241e8cde0b105e \
           file://add-vformat-hevc.patch \
           file://libamcodec.pc \
"