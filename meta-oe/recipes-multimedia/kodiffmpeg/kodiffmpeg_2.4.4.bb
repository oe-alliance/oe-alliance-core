PACKAGECONFIG ??= "pthreads zlib bzip2 libvorbis vfp"


PR = "r0"

SRC_URI = "https://github.com/xbmc/FFmpeg/archive/${PV}-Helix.tar.gz"
SRC_URI[md5sum] = "19b5d29ef6b5a6fc202c652fe3905d9b"
SRC_URI[sha256sum] = "cbaac116254004f993a0c62bb77e13745c9ac00960f2a0ef088baf09b0ad73de"

require kodiffmpeg.inc