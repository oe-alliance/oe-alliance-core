require dreambox-dvb-modules.inc

SRC_URI[modules.md5sum] = "ecb6143fd7979c689344ed49a54fc0fc"
SRC_URI[modules.sha256sum] = "dc2ec248e018df465c4f88e8cc33bc9b90263911699648fb800452d89e570414"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi