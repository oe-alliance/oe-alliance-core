inherit allarch

require conf/license/license-gplv2.inc

TBS_USB_BRIDGE ?= ""

RRECOMMENDS:${PN} = "${TBS_USB_BRIDGE}"

PV = "2.0"
ALLOW_EMPTY:${PN} = "1"
