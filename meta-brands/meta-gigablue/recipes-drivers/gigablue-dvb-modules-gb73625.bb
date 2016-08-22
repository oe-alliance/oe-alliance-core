SRCDATE = "20160821"

KV = "4.0.1"

SRC_URI[md5sum] = "804fb4af050e623dc4b5a83db4805b16"
SRC_URI[sha256sum] = "1c427febfabce49401d21ce05fd5a9518453da15bbb2861bd406ef5c5fa6587f"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
