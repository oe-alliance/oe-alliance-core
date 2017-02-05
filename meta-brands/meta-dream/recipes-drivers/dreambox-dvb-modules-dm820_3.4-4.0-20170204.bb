KV = "3.4-4.0"
DRIVERDATE = "20170204"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "bc27665a498885ce5a8a646ceef9f1c5"
SRC_URI[dm820.sha256sum] = "fb64c27687b84d0c612a2495e8de110d2d5aa41a8fb3835bade771c524723754"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"