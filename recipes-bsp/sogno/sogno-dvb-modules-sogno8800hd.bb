KV = "3.9.7"
SRCDATE = "20131119"

SRC_URI[md5sum] = "efa2b6724784ef52ee27f484fa81f798"
SRC_URI[sha256sum] = "7fa25265796af86e812e408334644bd777f548743be090330951399f9ab2b8a5"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
