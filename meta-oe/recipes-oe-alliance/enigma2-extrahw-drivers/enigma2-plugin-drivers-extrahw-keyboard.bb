SUMMARY = "Collection of keyboard drivers"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = "\
	kernel-module-hid \
	kernel-module-hid-generic \
	kernel-module-usbhid \
	kernel-module-hid-ezkey \
	kernel-module-hid-a4tech \
	kernel-module-hid-belkin \
	kernel-module-hid-logitech \
	kernel-module-hid-microsoft \
	"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
