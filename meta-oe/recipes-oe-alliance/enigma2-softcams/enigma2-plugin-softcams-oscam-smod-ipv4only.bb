include oscam-smod.inc

EXTRA_OECMAKE += "\
	-DIPV6SUPPORT=0 \
	"

DESCRIPTION += "Note: You should never need this IPv4-ONLY version!"

RPROVIDES_${PN}  = "softcam-${BINFILE}-ipv4"
RREPLACES_${PN}  = "softcam-${BINFILE}"
RCONFLICTS_${PN} = "softcam-${BINFILE}"
