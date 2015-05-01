SRCDATE = "20150425"

KV = "3.14.2"

SRC_URI[md5sum] = "71d384cd1e51c94a74a76d34df77a0af"
SRC_URI[sha256sum] = "db860f23ef91fa6f24132459eec8b459f8d773442f7dac604fb334ab4b97a923"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
