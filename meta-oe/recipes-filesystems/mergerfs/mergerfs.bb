SUMMARY = "a featureful union filesystem"
DESCRIPTION = "mergerfs is a union filesystem geared towards simplifying \
storage and management of files across numerous commodity storage devices. \
It is similar to mhddfs, unionfs, and aufs."

HOMEPAGE = "https://github.com/trapexit/mergerfs"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=27b6424853cee7f70464df20783b2658"

inherit autotools gitpkgv

SRCREV = "6418b00ac310fbde5c68d33903a244b6f4b9f8b5"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/trapexit/mergerfs.git"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "CONFIGURE_FLAGS='--host=${HOST_SYS} --build=${BUILD_SYS} --target=${TARGET_SYS} --with-libtool-sysroot=${STAGING_DIR_HOST} --prefix=${prefix}'"

do_compile () {
    #Automake dir is not correctly detected in cross compilation case
    export AUTOMAKE_DIR="$(automake --print-libdir)"
    export ACLOCAL_FLAGS="--system-acdir=${ACLOCALDIR}/ ${ACLOCALEXTRAPATH}"

    sed -i -e "s:/usr/include:${STAGING_INCDIR}:g" ${S}/Makefile
    cd ${S}/libfuse
    autoreconf -f -i
    autoconf
    ./configure --host=${HOST_SYS} --build=${BUILD_SYS} --target=${TARGET_SYS} --prefix=${prefix} --enable-lib --disable-util
    cd ${S}
    sed -e 's#/usr/local#${WORKDIR}/build/usr#' -i Makefile
    make src/version.hpp
    make install
}

do_install () {
        mkdir -p ${D}${bindir}
        install -m 755 ${WORKDIR}/build/${bindir}/mergerfs ${D}${bindir}
        ln -s mergerfs ${D}${bindir}/mount.mergerfs
}
