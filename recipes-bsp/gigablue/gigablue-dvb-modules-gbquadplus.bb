SRCDATE = "20140418"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "b19713eb2d34b3812b0e3040bc8bb766"
SRC_URI[sha256sum] = "6737cb99c807a55af9220f713e8ad177588c454672d794150f7ceb73f0ec03ff"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquadplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

