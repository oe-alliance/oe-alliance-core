PACKAGES =+ "${PN}-realpath ${PN}-truefalse"

FILES:${PN}-truefalse = "${base_bindir}/true.${PN} ${base_bindir}/false.${PN}"
FILES:${PN}-realpath = "${bindir}/realpath.${PN}"

RRECOMMENDS:${PN}:append:class-target = "${PN}-realpath ${PN}-stdbuf ${PN}-truefalse"

ALTERNATIVE:${PN}:remove = "realpath stdbuf true false"
ALTERNATIVE:${PN}-truefalse = "true false"
ALTERNATIVE:${PN}-realpath = "realpath"
ALTERNATIVE:${PN}-stdbuf = "stdbuf"
PACKAGE_NO_LOCALE = "1"
