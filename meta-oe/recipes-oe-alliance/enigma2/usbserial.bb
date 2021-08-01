SUMMARY = "meta package for usbserial drivers"
RDEPENDS:${PN} = "kernel-module-usbserial kernel-module-ftdi-sio kernel-module-pl2303"
RRECOMMENDS:${PN} = "kernel-module-belkin-sa kernel-module-keyspan"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"
