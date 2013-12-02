KV = "3.9.7"
SRCDATE = "20131202"

SRC_URI[md5sum] = "57be3f231a76faa5db25c7ae77a355fd"
SRC_URI[sha256sum] = "76bcde8e1281d20f9334b58fab52499747d60af3ecee7f691518fda3e1f32bde"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
