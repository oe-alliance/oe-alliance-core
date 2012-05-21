PRINC = "1"

EXTRA_OECONF += " --disable-udev"

DEPENDS := "${@oe_filter_out('udev', '${DEPENDS}', d)}"
