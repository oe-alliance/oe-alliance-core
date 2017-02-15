SUMMARY = "Kodi Media Center"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://JsonSchemaBuilder.cpp;beginline=2;endline=18;md5=1f67721215c03f66545390f6e45b99c7"

BRANCH = "Jarvis"
PV = "16.1"

SRC_URI = "https://github.com/xbmc/xbmc/archive/16.1-Jarvis.tar.gz"
SRC_URI[md5sum] = "79358ad5f77f42c5498a9412d5a78dd5"
SRC_URI[sha256sum] = "7d82c8aff2715c83deecdf10c566e26105bec0473af530a1356d4c747ebdfd10"

inherit autotools-brokensep gettext native

S = "${WORKDIR}/xbmc-${PV}-${BRANCH}/tools/depends/native/JsonSchemaBuilder/src"

do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o    -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

