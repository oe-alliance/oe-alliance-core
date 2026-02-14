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
