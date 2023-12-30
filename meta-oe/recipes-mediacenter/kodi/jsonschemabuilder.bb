SUMMARY = "Kodi json schema builder"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://JsonSchemaBuilder.cpp;beginline=2;endline=18;md5=1f67721215c03f66545390f6e45b99c7"

SRCREV = "${AUTOREV}"

PV = "21.0+gitr"
SRC_URI = "git://github.com/xbmc/xbmc.git;branch=master;protocol=https"

inherit autotools-brokensep gettext native

S = "${WORKDIR}/git/tools/depends/native/JsonSchemaBuilder/src"

do_compile:prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o    -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

BBCLASSEXTEND = "native"
