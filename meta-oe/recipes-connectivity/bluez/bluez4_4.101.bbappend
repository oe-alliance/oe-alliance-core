EXTRA_OECONF += " --disable-udevrules --enable-hidd"

DEPENDS := "${@oe_filter_out('udev', '${DEPENDS}', d)}"