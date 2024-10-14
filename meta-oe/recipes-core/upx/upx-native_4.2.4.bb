SUMMARY = "The Ultimate Packer for eXecutables."
DESCRIPTION = "Executable packer for several executable formats."
HOMEPAGE = "https://upx.github.io"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=353753597aa110e0ded3508408c6374a"

SRC_URI = "https://github.com/upx/upx/releases/download/v${PV}/upx-${PV}-src.tar.xz"
SRC_URI[sha256sum] = "5ed6561607d27fb4ef346fc19f08a93696fa8fa127081e7a7114068306b8e1c4"

S = "${UNPACKDIR}/upx-${PV}-src"

inherit native

do_install(){
    install -d ${D}${bindir}
    install -m 0755 ${S}/build/release/upx ${D}${bindir}/
}

CLEANBROKEN = "1"
