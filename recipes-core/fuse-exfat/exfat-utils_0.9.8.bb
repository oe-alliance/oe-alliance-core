PR = "r1"

# DEPENDS = "fuse"
# RDEPENDS_${PN} = "fuse"

inherit scons

require conf/license/license-gplv2.inc

SRC_URI = "http://exfat.googlecode.com/files/${PN}-${PV}.tar.gz file://exfat-utils-sconstruct.patch"

PACKAGES =+ "exfat-mkfs exfat-label exfat-fsck exfat-dump"

# utils is a meta package that installs them all
RDEPENDS_${PN} = "exfat-mkfs exfat-label exfat-fsck exfat-dump"
FILES_exfat-mkfs = "${sbindir}/mkexfatfs"
FILES_exfat-label = "${sbindir}/exfatlabel"
FILES_exfat-fsck = "${sbindir}/exfatfsck"
FILES_exfat-dump = "${sbindir}/dumpexfat"

do_install() {
    install -d ${D}/${sbindir}
    install -m 0755 ${S}/dump/dumpexfat ${D}/${sbindir}/
    install -m 0755 ${S}/fsck/exfatfsck ${D}/${sbindir}/
    install -m 0755 ${S}/label/exfatlabel ${D}/${sbindir}/
    install -m 0755 ${S}/mkfs/mkexfatfs ${D}/${sbindir}/
}

SRC_URI[md5sum] = "a14c4bab6920121c874f448b5bfe4017"
SRC_URI[sha256sum] = "97d8c77e048d0fdc2e5425ff22a139306ce3f1575b58e56c26bb968930a423bb"

