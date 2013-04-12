DESCRIPTION = "genromfs generates romfs filesystem images"
HOMEPAGE = "http://jf.koan19.org/dev/romfs/"
LICENSE = "GPLv2+"

PV = "0.5.7+git${SRCREV}"
PR = "1"

inherit native

SRCREV = "e4225b49a7be0ae9d39e98f2175dd674c0d6b1ea"
SRC_URI = "git://github.com/chexum/genromfs;protocol=git;branch=master"

S = "${WORKDIR}/git"

BBCLASSEXTEND = "native nativesdk"

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

