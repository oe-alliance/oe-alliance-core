SUMMARY = "The Ultimate Packer for eXecutables."
DESCRIPTION = "Executable packer for several executable formats."
HOMEPAGE = "https://upx.github.io"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=353753597aa110e0ded3508408c6374a"

SRCREV = "071579b5b6ba1acd7d324d1643425126485bb99b"
PV = "5.0.1"
PKGV = "5.0.1"

SRC_URI = "git://github.com/upx/upx;protocol=https;branch=devel"

S = "${UNPACKDIR}/git"

do_configure[network] = "1"
do_configure:prepend() {
    git submodule update --init --recursive
}

do_install(){
    install -d ${D}${bindir}
    install -m 0755 ${S}/build/release/upx ${D}${bindir}/
}

CLEANBROKEN = "1"

BBCLASSEXTEND = "native"
