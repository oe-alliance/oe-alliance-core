inherit update-alternatives

ALTERNATIVE_${PN} = "x-window-manager"
ALTERNATIVE_TARGET[x-window-manager] = "${bindir}/matchbox-window-manager"
ALTERNATIVE_PRIORITY[x-window-manager] = "50"
