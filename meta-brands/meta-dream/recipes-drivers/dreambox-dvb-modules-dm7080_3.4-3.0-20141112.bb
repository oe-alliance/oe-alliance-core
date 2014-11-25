require dreambox-dvb-modules.inc

SRC_URI[dm7080.md5sum] = "1671a36fe6ed4e9e7b0c86b69495bc03"
SRC_URI[dm7080.sha256sum] = "17919bd197f0ca155580b51f61d561d555c058b9d2ae825bb2224957c4959eff"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi