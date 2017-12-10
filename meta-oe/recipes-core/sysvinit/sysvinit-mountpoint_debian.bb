#
# mountpoint command is shared by init scripts in multiple packages
# such as udev, nfs-common. Therefore, mountpoint should be moved
# from sysvinit initscript package and implemented as one package
# in order to create small systems without sysvinit scripts.
#

require sysvinit.inc

PR = "${INC_PR}.0"

DPN = "sysvinit"

# ignore the top-level Makefile and build only mountpoint
EXTRA_OEMAKE += "-C src mountpoint"

do_install() {
	install -d ${D}${base_bindir}
	install -m 0755 ${S}/src/mountpoint ${D}${base_bindir}
}
