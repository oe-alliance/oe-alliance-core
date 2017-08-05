SRCDATE = "20160319"

KV = "3.14.28"

SRC_URI[md5sum] = "7c87a28051caaf1be65f7621e189afc4"
SRC_URI[sha256sum] = "23b6a156d9943ee37348bfffcefa07051c3aea6e614199f41d46542f27bd6b22"

SRC_URI = "http://impex-sat.de/gigablue/drivers/gigablue-drivers-${KV}-BCM7252-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
