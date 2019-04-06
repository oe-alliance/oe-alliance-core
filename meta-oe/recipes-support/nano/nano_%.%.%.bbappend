inherit update-alternatives

EXTRA_OECONF_prepend = "--bindir=/bin"

ALTERNATIVE_${PN} = "editor"
ALTERNATIVE_LINK_NAME[editor] = "${base_bindir}/editor"
ALTERNATIVE_TARGET[editor] = "${base_bindir}/nano"
ALTERNATIVE_PRIORITY = "150"
