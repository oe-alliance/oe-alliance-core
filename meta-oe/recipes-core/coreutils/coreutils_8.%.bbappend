PACKAGES =+ "${PN}-realpath ${PN}-stdbuf ${PN}-truefalse"

FILES_${PN}-truefalse = "${base_bindir}/true.${PN} ${base_bindir}/false.${PN}"
FILES_${PN}-realpath = "${bindir}/realpath.${PN}"
FILES_${PN}-stdbuf = "${bindir}/stdbuf.${PN} ${libdir}/coreutils/libstdbuf.so"

RRECOMMENDS_${PN}_append_class-target = "${PN}-realpath ${PN}-stdbuf ${PN}-truefalse"

ALTERNATIVE_${PN}_remove = "realpath stdbuf true false"
ALTERNATIVE_${PN}-truefalse = "true false"
ALTERNATIVE_${PN}-realpath = "realpath"
ALTERNATIVE_${PN}-stdbuf = "stdbuf"
