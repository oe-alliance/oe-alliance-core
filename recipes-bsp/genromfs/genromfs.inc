DESCRIPTION = "genromfs generates romfs filesystem images"
HOMEPAGE = "http://jf.koan19.org/dev/romfs/"
LICENSE = "GPLv2+"
INC_PR = "0"

EXTRA_OEMAKE = '\
    "CC=${CC}" \
    "LDFLAGS=${LDFLAGS}" \
    "prefix=${prefix}" \
    "bindir=${bindir}" \
    "mandir=${mandir}" \
'

do_compile () {
    oe_runmake 'CFLAGS=${CFLAGS} -DVERSION=\"$(VERSION)\"'
}

do_install () {
    oe_runmake 'PREFIX=${D}' install
}

