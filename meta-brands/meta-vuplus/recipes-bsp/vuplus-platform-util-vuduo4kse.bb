require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

RDEPENDS_${PN} += "mmc-utils"

PV="17.1"
SRCDATE = "20210527"
SRCDATE_PR = "r0"
PR_append = ".0"

S="${WORKDIR}/platform-util-vuduo4kse"

SRC_URI[md5sum] = "29805f9bcccfa65330c756ee8c8109b4"
SRC_URI[sha256sum] = "18f22cb956f5cdf751a435a2580aab075e0bac65b2465a802f1a480e3b77be7f"