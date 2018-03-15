EXTRA_OECONF += " --disable-udev"

DEPENDS := "${@oe.utils.str_filter_out('udev', '${DEPENDS}', d)}"
