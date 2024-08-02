EXTRA_OECMAKE += "\
	-DHAVE_PCSC=0 \
"

require oscam-common.inc

PV = "1.30+git"
S = "${WORKDIR}/git"
UNPACKDIR = "${WORKDIR}/sources"
