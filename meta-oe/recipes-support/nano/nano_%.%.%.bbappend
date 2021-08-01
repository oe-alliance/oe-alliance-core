inherit update-alternatives

EXTRA_OECONF:prepend = "--bindir=/bin"

ALTERNATIVE:${PN} = "editor"
ALTERNATIVE_LINK_NAME[editor] = "${base_bindir}/editor"
ALTERNATIVE_TARGET[editor] = "${base_bindir}/nano"
ALTERNATIVE_PRIORITY = "150"
