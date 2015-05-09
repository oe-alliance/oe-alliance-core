SRCDATE = "20150506"

KV = "3.9.6"

SRC_URI[md5sum] = "aec5fc084cb452b912189efcc7df0fd6"
SRC_URI[sha256sum] = "424d0daab41263ec3cfa3acb92dbde0485a69aecb7468c406da5b96e85306c26"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7325-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
