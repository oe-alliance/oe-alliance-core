SUMMARY = "Perl extension for generating and using LALR parsers."
HOMEPAGE = "https://metacpan.org/pod/Parse::Yapp"
DESCRIPTION = "Parse::Yapp (Yet Another Perl Parser compiler) is a collection of modules that let you generate and use yacc like thread safe (reentrant) parsers with perl \
object oriented interface. The script yapp is a front-end to the Parse::Yapp module and let you easily create a Perl OO parser from an input grammar file."
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = "file://lib/Parse/Yapp.pm;beginline=510;endline=521;md5=ae7c63c9a4a25e7093c631471a6d0342"

SRC_URI = "https://cpan.metacpan.org/authors/id/W/WB/WBRASWELL/Parse-Yapp-${PV}.tar.gz"

SRC_URI[md5sum] = "69584d5b0f0304bb2a23cffcd982c5de"
SRC_URI[sha256sum] = "3810e998308fba2e0f4f26043035032b027ce51ce5c8a52a8b8e340ca65f13e5"

S = "${WORKDIR}/Parse-Yapp-${PV}"

inherit cpan

EXTRA_PERLFLAGS = "-I ${PERLHOSTLIB}"

BBCLASSEXTEND = "native"
