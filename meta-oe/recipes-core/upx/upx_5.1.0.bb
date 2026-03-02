SUMMARY = "The Ultimate Packer for eXecutables."
DESCRIPTION = "Executable packer for several executable formats."
HOMEPAGE = "https://upx.github.io"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=353753597aa110e0ded3508408c6374a"

SRCREV = "779acb1cd45277f55c8e57c943900f37cbac1207"

SRC_URI = "gitsm://github.com/upx/upx.git;protocol=https;branch=master"

do_install(){
    install -d ${D}${bindir}
    install -m 0755 ${S}/build/release/upx ${D}${bindir}/
}

CLEANBROKEN = "1"

BBCLASSEXTEND = "native"
