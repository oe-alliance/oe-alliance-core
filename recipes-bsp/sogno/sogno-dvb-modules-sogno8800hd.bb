KV = "3.9.7"
SRCDATE = "20131131"

SRC_URI[md5sum] = "dfe21918704e311a7dfc5441832fc8b6"
SRC_URI[sha256sum] = "ef4bffff6854f80b9ef10e423d7dec560d49557610fe23b53da6df19c6ac1b31"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
