KV = "3.9.7"
SRCDATE = "20131206"

SRC_URI[md5sum] = "bb608ca77521e812381ff5f5d5685fab"
SRC_URI[sha256sum] = "705e720e275c27e1c827d4bff7792bc68cfef8b109b479e4ad0f0a1e6c4cfe50"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
