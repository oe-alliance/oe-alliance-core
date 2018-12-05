require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

RDEPENDS_${PN} += "mmc-utils"

PV="18.1"
SRCDATE = "20181204"
SRCDATE_PR = "r0"
PR_append = ".1"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "559af1ff41693df27ff6e4c608182acf"
SRC_URI[sha256sum] = "fbd87b07cfab527e01e5ec8d33089799397ed28b94c36230d05fecfd2aec4e8e"