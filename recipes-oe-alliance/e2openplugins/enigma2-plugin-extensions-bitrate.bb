MODULE = "Bitrate"
DESCRIPTION = "Bitrate viewer"

require openplugins-replace-pli.inc
PR="r1"

require openplugins.inc

inherit autotools

EXTRA_OECONF = "--with-boxtype=${MACHINE} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR}"

FILES_${PN} = "${libdir} ${bindir}"

require assume-gplv2.inc
