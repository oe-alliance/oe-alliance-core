SUMMARY = "USB DVB driver for EM28xx chipset"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

SRC_URI = "file://delock61959_em28xx.conf"

RDEPENDS_${PN} = " \
${DVBPROVIDER}-module-em28xx-dvb \
${DVBPROVIDER}-module-tda18271c2dd1 \
${DVBPROVIDER}-module-drxk \
${DVBPROVIDER}-module-tuner \
firmware-dvb-fe-drxk-delock \
"

PV = "1.0"
PR = "r1"


PACKAGES = "${PN}"
FILES_${PN} += "${sysconfdir}/modprobe.d"

do_install() {
  install -m 0644 delock61959_em28xx.conf ${D}/etc/modprobe.d/em28xx.conf
}
