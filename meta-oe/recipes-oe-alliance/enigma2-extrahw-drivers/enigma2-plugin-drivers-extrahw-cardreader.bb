SUMMARY = "Collection of card reader drivers"
inherit allarch

require conf/license/license-gplv2.inc

DEPENDS = "\
	pcsc-lite \
	pcsc-tools \
	"

RRECOMMENDS:${PN} = "\
	kernel-module-usbserial \
	kernel-module-belkin-sa \
	kernel-module-ch341 \
	kernel-module-cp210x \
	kernel-module-ftdi-sio \
	kernel-module-iuu-phoenix \
	kernel-module-keyspan \
	kernel-module-pl2303 \
	pcsc-lite \
	pcsc-tools \
	"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
