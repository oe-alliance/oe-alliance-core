SUMMARY = "Tool to allow editing of RPATH and interpreter fields in ELF binaries"
DESCRIPTION = "PatchELF is a simple utility for modifying existing ELF executables and libraries."
HOMEPAGE = "https://github.com/NixOS/patchelf"

LICENSE = "GPL-3.0-only"

FILESEXTRAPATHS:prepend := "${THISDIR}/patchelf:"

SRC_URI = "git://github.com/NixOS/patchelf;protocol=https;branch=master \
           file://handle-read-only-files.patch"

SRC_URI:append:sh4 = " file://sh4-readd-c11-support.patch"

SRCREV = "bf3f37ec29edcdb3e2a163edaf84aeece39f8c9d"

S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

inherit autotools

BBCLASSEXTEND = "native nativesdk"
