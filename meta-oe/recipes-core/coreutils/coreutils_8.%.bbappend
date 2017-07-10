PACKAGES =+ "${PN}-realpath ${PN}-stdbuf"

FILES_${PN}-realpath = "${bindir}/realpath.${PN}"
FILES_${PN}-stdbuf = "${bindir}/stdbuf.${PN} ${libdir}/coreutils/libstdbuf.so"

RRECOMMENDS_${PN}_append_class-target = "${PN}-realpath ${PN}-stdbuf"

ALTERNATIVE_${PN}_remove = "realpath stdbuf"
ALTERNATIVE_${PN}-realpath = "realpath"
ALTERNATIVE_${PN}-stdbuf = "stdbuf"
