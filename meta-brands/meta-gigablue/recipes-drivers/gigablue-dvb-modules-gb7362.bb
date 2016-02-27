SRCDATE = "20160225"

KV = "4.0.1"

SRC_URI[md5sum] = "eed7002d3401ea55eb3aea9ea3beeeec"
SRC_URI[sha256sum] = "c76d99c2908cecec0482a15822975982ad3489667547e07addd8c1c85198c4e2"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
