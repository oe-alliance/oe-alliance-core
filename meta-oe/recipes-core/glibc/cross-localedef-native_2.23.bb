SUMMARY = "Cross locale generation tool for glibc"
HOMEPAGE = "http://www.gnu.org/software/libc/libc.html"
SECTION = "libs"
LICENSE = "LGPL-2.1"

LIC_FILES_CHKSUM = "file://LICENSES;md5=e9a558e243b36d3209f380deb394b213 \
      file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
      file://posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
      file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

SRCBRANCH ?= "release/${PV}/master"
PV = "2.23"
SRCREV_glibc ?= "b039fd85db0717aca309b61925d00a5a6547a649"
SRCREV_localedef ?= "5a81ff9f06a7a808d4c3d37bbf34077a4c5902ed"

GLIBC_GIT_URI ?= "git://sourceware.org/git/glibc.git"

# Tell autotools that we're working in the localedef directory
#
AUTOTOOLS_SCRIPT_PATH = "${S}/localedef"

inherit autotools
inherit native

FILESEXTRAPATHS =. "${FILE_DIRNAME}/${PN}:${FILE_DIRNAME}/glibc:"

SRC_URI = "${GLIBC_GIT_URI};branch=${SRCBRANCH};name=glibc \
           git://github.com/kraj/localedef;branch=master;name=localedef;destsuffix=git/localedef \
           file://0016-timezone-re-written-tzselect-as-posix-sh.patch \
           file://0017-Remove-bash-dependency-for-nscd-init-script.patch \
           file://0018-eglibc-Cross-building-and-testing-instructions.patch \
           file://0019-eglibc-Help-bootstrap-cross-toolchain.patch \
           file://0021-eglibc-Clear-cache-lines-on-ppc8xx.patch \
           file://0022-eglibc-Resolve-__fpscr_values-on-SH4.patch \
           file://0024-eglibc-Forward-port-cross-locale-generation-support.patch \
           file://0025-Define-DUMMY_LOCALE_T-if-not-defined.patch \
           file://0024-localedef-add-to-archive-uses-a-hard-coded-locale-pa.patch \
           file://0001-Include-locale_t.h-compatibility-header.patch \
           file://add-cross-localedef-hardlink.patch \
           file://allow-compile-separate-from-util-linux-hardlink.patch \
           file://0030-glibc-2-23-add-no-hard-links-option.patch \
"
# Makes for a rather long rev (22 characters), but...
#
SRCREV_FORMAT = "glibc_localedef"

S = "${WORKDIR}/git"

EXTRA_OECONF = "--with-glibc=${S}"
CFLAGS += "-fgnu89-inline -std=gnu99 -D_IO_fwide=fwide -DIS_IN\(x\)='0'"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${B}/localedef ${D}${bindir}/cross-localedef
	install -m 0755 ${B}/cross-localedef-hardlink ${D}${bindir}/cross-localedef-hardlink
}
