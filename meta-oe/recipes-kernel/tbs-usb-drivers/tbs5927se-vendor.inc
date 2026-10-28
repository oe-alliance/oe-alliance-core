HOMEPAGE = "https://www.tbsdtv.com/download/index.html?id=218&path=4"

# TBS publishes the SE sources separately from the linux_media Git tree.
SRC_URI = "https://www.tbsiptv.com/download/common/tbsdvb_v1015.tar.bz2"
SRC_URI[sha256sum] = "4b56fe7d2ec487d4af6117acd27ac953a8886ea03d91878737081da2fabb9b40"

S = "${UNPACKDIR}/tbsdvb"
