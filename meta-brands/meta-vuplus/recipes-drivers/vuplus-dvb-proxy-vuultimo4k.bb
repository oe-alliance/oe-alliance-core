PV = "3.14.28"
KV = "${PV}-1.12"
SRCDATE = "20250706"
SRCDATE_PR = "r2"

require vuplus-dvb-proxy.inc

PR:append = ".1"
VENDOR_DRIVER_FIX_PROFILE = "ultimo"
require recipes-bsp/vendor-driver-fixes/vendor-driver-fixes.inc

SRC_URI[md5sum] = "ad0c4376a29abb9ba57efb39ec97f38c"
SRC_URI[sha256sum] = "bb10691d361812bcfb9eaea541ae85f2a72d3956ab74c64406088583b39e818a"

require vuplus-stcbind-boot.inc
