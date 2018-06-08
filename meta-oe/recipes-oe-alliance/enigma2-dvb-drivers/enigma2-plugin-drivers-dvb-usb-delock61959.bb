SUMMARY = "USB DVB driver for Delock61959 USB DVB-C/T based on EM28xx chipset"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
${DVBPROVIDER}-module-em28xx \
${DVBPROVIDER}-module-em28xx-dvb \
${DVBPROVIDER}-module-em28xx-alsa \
${DVBPROVIDER}-module-em28xx-rc \
${DVBPROVIDER}-module-tda18271c2dd \
${DVBPROVIDER}-module-drxk \
firmware-dvb-fe-drxk_a3 \
"

PV = "1.0"
PR = "r0"

PACKAGES = "${PN}"

pkg_postinst_${PN} () {
#!/bin/sh
if [ -f ${D}/lib/firmware/dvb-demod-drxk-01.fw ]; then
  rm ${D}/lib/firmware/dvb-demod-drxk-01.fw
fi
ln -s ${D}/lib/firmware/drxk_a3.mc ${D}/lib/firmware/dvb-demod-drxk-01.fw
echo "options em28xx cards=89 usb_xfer_mode=0" > ${D}/etc/modprobe.d/em28xx.conf
chmod 0644 ${D}/etc/modprobe.d/em28xx.conf
}

pkg_postrm_${PN} () {
#!/bin/sh
if [ -f /lib/firmware/dvb-demod-drxk-01.fw ]; then
  rm /lib/firmware/dvb-demod-drxk-01.fw
fi
if [ -f /etc/modprobe.d/em28xx.conf ]; then
  rm /etc/modprobe.d/em28xx.conf
fi
}
