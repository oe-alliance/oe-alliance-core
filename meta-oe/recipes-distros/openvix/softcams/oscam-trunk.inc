EXTRA_OECMAKE += "\
	-DHAVE_PCSC=0 \
"

require oscam-common.inc

PV = "1.20+svn${SRCPV}"
S = "${WORKDIR}/trunk"
