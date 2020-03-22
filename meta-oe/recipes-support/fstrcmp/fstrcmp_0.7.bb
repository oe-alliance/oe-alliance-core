SUMMARY = "The fstrcmp project provides a library that is used to make fuzzy comparisons of strings and byte arrays, including multi-byte character strings."
HOMEPAGE = "http://fstrcmp.sourceforge.net/"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84269fe9822f573d2cc8131b63febc57"

SRC_URI = "http://fstrcmp.sourceforge.net/fstrcmp-${PV}.D001.tar.gz \
           file://nopdf.patch \
          "
SRC_URI[md5sum] = "9c440bbdfcad9fd22e38f2388715b0cc"
SRC_URI[sha256sum] = "e4018e850f80700acee8da296e56e15b1eef711ab15157e542e7d7e1237c3476"

S = "${WORKDIR}/${BPN}-${PV}.D001"

inherit autotools-brokensep manpages

DEPENDS = "groff-native"

export LIBTOOL = "${HOST_SYS}-libtool"

BBCLASSEXTEND = "native nativesdk"
