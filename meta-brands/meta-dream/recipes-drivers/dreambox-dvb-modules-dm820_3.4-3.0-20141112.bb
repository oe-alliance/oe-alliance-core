KV = "3.4-3.0"
DRIVERDATE = "20141112"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "588635b2b163a3f8bef7a1e0285ea34a"
SRC_URI[dm820.sha256sum] = "4d202f421453ac21d3ef9a0fd0db3845ef3e05b1c8b08afd82d72b92c15b43ed"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"