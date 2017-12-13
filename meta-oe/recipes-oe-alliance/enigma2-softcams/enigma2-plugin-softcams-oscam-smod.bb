include oscam-smod.inc

EXTRA_OECMAKE += "\
	-DIPV6SUPPORT=1 \
	"

DESCRIPTION += "- IPv6 support\nThis version can connect to servers using IPv6 and/or IPv4."

RPROVIDES_${PN}  = "softcam-${BINFILE}"
RREPLACES_${PN}  = "softcam-${BINFILE}-ipv4"
RCONFLICTS_${PN} = "softcam-${BINFILE}-ipv4"
