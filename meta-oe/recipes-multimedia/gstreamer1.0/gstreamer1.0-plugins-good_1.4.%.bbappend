PR="r1"
FILESEXTRAPATHS_prepend := "${THISDIR}:"

PACKAGECONFIG += "orc cairo flac gudev jpeg libpng soup speex taglib wavpack"

require mips-only.inc
