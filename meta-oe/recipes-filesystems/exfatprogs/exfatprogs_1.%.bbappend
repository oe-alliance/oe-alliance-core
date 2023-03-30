PACKAGES =+ "exfat-mkfs exfat-label exfat-fsck exfat-dump"

RDEPENDS:${PN} = "exfat-mkfs exfat-label exfat-fsck exfat-dump"
FILES:exfat-mkfs = "{sbindir}mkfs.exfat ${sbindir}/mkexfatfs"
FILES:exfat-label = "${sbindir}/exfatlabel"
FILES:exfat-fsck = "${sbindir}/fsck.exfat ${sbindir}/exfatfsck"
FILES:exfat-dump = "${sbindir}/dump.exfat ${sbindir}/dumpexfat"

do_install:append() {
    install -d ${D}${sbindir}
    install -m 0755 ${B}/mkfs/mkfs.exfat ${D}${sbindir}/
    ln -sf mkfs.exfat  ${D}${sbindir}/mkexfatfs
    install -m 0755 ${B}/label/exfatlabel ${D}${sbindir}/
    install -m 0755 ${B}/fsck/fsck.exfat ${D}${sbindir}/
    ln -sf fsck.exfat ${D}${sbindir}/exfatfsck
    install -m 0755 ${B}/dump/dump.exfat ${D}${sbindir}/
    ln -sf dump.exfat ${D}${sbindir}/dumpexfat
}
