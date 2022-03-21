SUMMARY = "Collection of bluetooth drivers"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = "\
	kernel-module-bluetooth \
	kernel-module-hidp \
	kernel-module-btusb \
	kernel-module-btbcm \
	kernel-module-btintel \
	kernel-module-btrtl \
	"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
