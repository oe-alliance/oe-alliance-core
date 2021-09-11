DESCRIPTION = "autossh"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://autossh.c;beginline=1;endline=24;md5=90bbe42c2e8146adf5a08a7e911c8ffd"

SRC_URI = " \
	http://www.harding.motd.ca/autossh/${P}.tgz \
	file://0001-remove-hosttools-path-for-ssh.patch \
	"

inherit autotools-brokensep

TARGET_CC_ARCH += "${LDFLAGS}"

# Install target is broken (attempts to install in /usr/ and ignores
# DESTDIR environment)
do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${B}/autossh ${D}${bindir}/autossh
}

SRC_URI[md5sum] = "2b804bc1bf6d2f2afaa526d02df7c0a2"
SRC_URI[sha256sum] = "5fc3cee3361ca1615af862364c480593171d0c54ec156de79fc421e31ae21277"
