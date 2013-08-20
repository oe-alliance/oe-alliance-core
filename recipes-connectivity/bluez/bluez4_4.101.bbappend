PRINC = "1"

EXTRA_OECONF += " --disable-udevrules"

DEPENDS := "${@oe_filter_out('udev', '${DEPENDS}', d)}"