KV = "3.9.7"
SRCDATE = "20131212"

SRC_URI[md5sum] = "ffd5a0b330991390e201c49aa3bc2082"
SRC_URI[sha256sum] = "2c26dbb895c506c187e0f4890fbdb5a6bd119d749ef6829e64f5f5e05ec6aec3"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
