do_configure:prepend() {
    find "${UNPACKDIR}" -type f -name 'l_stdlib.h' -exec sed -i \
'/^extern void \*memchr/ i\
#ifdef memchr\
# undef memchr\
#endif' {} +
}
