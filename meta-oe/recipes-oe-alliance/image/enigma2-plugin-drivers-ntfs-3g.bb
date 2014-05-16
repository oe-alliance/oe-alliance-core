SUMMARY = "Allow writes to NTFS file systems"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "ntfs-3g"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
