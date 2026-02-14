PACKAGES =+ "${PN}-realpath ${PN}-truefalse"

FILES:${PN}-truefalse = "${base_bindir}/true.${PN} ${base_bindir}/false.${PN}"
FILES:${PN}-realpath = "${bindir}/realpath.${PN}"

RRECOMMENDS:${PN}:append:class-target = " ${PN}-realpath ${PN}-stdbuf ${PN}-truefalse"

ALTERNATIVE:${PN}:remove = "realpath stdbuf true false"
ALTERNATIVE:${PN}-truefalse = "true false"
ALTERNATIVE:${PN}-realpath = "realpath"
ALTERNATIVE:${PN}-stdbuf = "stdbuf"

PACKAGE_NO_LOCALE = "1"

do_configure:prepend() {
    find "${UNPACKDIR}" -type f -name 'stdlib.in.h' -exec sed -i \
'/^_GL_EXTERN_C void \*bsearch[[:space:]]/ i\
#ifdef bsearch\
# undef bsearch\
#endif' {} +

    find "${UNPACKDIR}" -type f -name 'wchar.in.h' -exec sed -i \
'/^_GL_EXTERN_C wchar_t \*wmemchr[[:space:]]/ i\
#ifdef wmemchr\
# undef wmemchr\
#endif' {} +
}
