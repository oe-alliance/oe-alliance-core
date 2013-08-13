SRCDATE = "20130813"

KV = "3.8.3"

SRC_URI[md5sum] = "26a6f89638cd306bccee2ecf81dedd68"
SRC_URI[sha256sum] = "5060e0e6ca0d80d1addbb3bd5ec2531ff29775a008bfb784a555bae4c7761615"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

