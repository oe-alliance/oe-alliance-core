SRCDATE = "20150826"

KV = "3.14.2"

SRC_URI[md5sum] = "ce664892328d90c5bd6adbd064c98420"
SRC_URI[sha256sum] = "93b7750c91a379770c7c8a9a66c1ce8e3879347362d7772593eec0625521e737"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
