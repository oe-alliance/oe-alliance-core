EXTRA_OECMAKE += "\
	-DIPV6SUPPORT=1 \
	"
SUMMARY:${PN}:append = " (IPv6/IPv4 Dual-Stack support)"

DESCRIPTION:${PN} += "\n \
 - IPv6 support\nThis version can connect to servers using IPv6 and/or IPv4."

RPROVIDES:${PN}  += "${CAMNAME}"
RREPLACES:${PN}  += "${CAMNAME}-ipv4"
RCONFLICTS:${PN} += "${CAMNAME}-ipv4"
