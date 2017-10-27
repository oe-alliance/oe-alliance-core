DEPENDS += "module-init-tools"
RDEPENDS_${PN} += "module-init-tools-depmod"
SRCDATE = "20100904"
PV = "0.0+hg${SRCDATE}"

SRC_URI = "hg://linuxtv.org/hg/;module=v4l-dvb;rev=${SRCREV} \
           file://defconfig \
           file://v4l-dvb-compat.patch \
           file://v4l-2.6.18-compat.patch \
           file://fix-blocking-demux.patch \
           file://basic-dvb-t2-support.patch \
           file://localversion.patch \
           file://fix-strip.patch \
           file://build-fix.patch \
           file://backport-1.patch \
           file://backport-2.patch \
           file://backport-3.patch \
           file://backport-4.patch \
           file://backport-5.patch \
           file://backport-6.patch \
           file://backport-7.patch \
           file://backport-8.patch \
           file://backport-9.patch \
           file://backport-a.patch \
           file://backport-b.patch \
           file://backport-c.patch \
           file://backport-d.patch \
           file://backport-e.patch \
           file://backport-f.patch \
           file://backport-g.patch \
           file://backport-h.patch \
           file://backport-i.patch \
           file://backport-j.patch \
           file://backport-k.patch \
           file://technisat_airstar_telestick_2.patch \
           file://v4l-dvb-as102.patch \
           file://v4l-dvb-a867.patch \
           file://v4l-dvb-tua9001.patch \
           file://v4l-dvb-af9033.patch \
           file://v4l-dvb-af9035.patch \
           file://v4l-dvb-tda18218.patch \
           file://v4l-dvb-af9013_fix_for_tda18218_tuner.patch \
           file://v4l-dvb-af9015_fix_for_tda18218_tuner.patch \
           file://v4l-dvb-af9013_add_firmware5.1.patch \
           file://v4l-dvb-af9015_fix_for_A815M.patch \
           file://v4l-dvb-em28xx_fix.patch \
           file://v4l-dvb-af9015_add_a850red.patch \
           file://v4l-dvb-smsdvb_fix_frontend.patch \
           file://v4l-dvb-smsdvb_always_load.patch \
"

SRCREV = "6e0befab696a"
S = "${WORKDIR}/v4l-dvb"

require v4l-dvb-modules.inc
