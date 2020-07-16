SUMMARY = "Kodi Media Center"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://JsonSchemaBuilder.cpp;beginline=2;endline=18;md5=1f67721215c03f66545390f6e45b99c7"

SRCREV = "c23b69d150637747de6432410d4c5f1df69a3252"

PV = "19.0+gitr${SRCPV}"
SRC_URI = "git://github.com/xbmc/xbmc.git;branch=master"

inherit autotools-brokensep gettext native

S = "${WORKDIR}/git/tools/depends/native/JsonSchemaBuilder/src"

do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o    -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

