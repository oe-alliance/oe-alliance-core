SRCDATE = "20161215"

KV = "4.0.1"

SRC_URI[md5sum] = "5bc13b17e145a414a1c4c50ec64ada3f"
SRC_URI[sha256sum] = "80221e028b912a0a48c8e602d80a495d88c0af10df27bf7607af421df4e0ab14"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
