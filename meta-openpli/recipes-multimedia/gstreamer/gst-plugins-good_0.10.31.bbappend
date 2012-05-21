DEPENDS := "${@oe_filter_out('^(gconf|pulseaudio)$', '${DEPENDS}', d)}"
DEPENDS := "${@oe_filter_out('gtk+', '${DEPENDS}', d)}"

PRINC = "1"

SRC_URI += "file://audioparser-raise-ranks.patch"

EXTRA_OECONF += "--enable-orc"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"
