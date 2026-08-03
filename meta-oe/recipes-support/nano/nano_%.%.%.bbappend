inherit update-alternatives

EXTRA_OECONF:prepend = "--bindir=/bin "

ALTERNATIVE:${PN} = "editor"
ALTERNATIVE_LINK_NAME[editor] = "${base_bindir}/editor"
ALTERNATIVE_TARGET[editor] = "${base_bindir}/nano"
ALTERNATIVE_PRIORITY = "150"

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
