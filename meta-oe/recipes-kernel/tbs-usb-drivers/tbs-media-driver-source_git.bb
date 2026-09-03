SUMMARY = "Shared source tree for out-of-tree TBS media modules"
HOMEPAGE = "https://github.com/tbsdtv/linux_media"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://drivers/media/usb/dvb-usb/tbs5927.c;beginline=1;endline=9;md5=189f5f41ea540aad0f13aaa32214dbeb"

SRC_URI = " \
    git://github.com/tbsdtv/linux_media.git;protocol=https;branch=latest \
    file://0001-stv091x-use-upstream-dvb-frontend-api.patch \
    file://0002-tbs5925-tbs5980-use-upstream-stv090x-api.patch \
    file://0003-tbs-media-use-upstream-dvb-api.patch \
    file://0004-tbs-combo-use-upstream-dvb-usb-multi-frontend.patch \
    file://0005-tbs-cx231xx-use-upstream-kernel-api.patch \
    file://0006-tbs-media-support-legacy-dvb-api.patch \
    file://0007-cx231xx-build-dvb-path-for-tbs5990-only.patch \
    file://0008-cx231xx-trim-external-stack-to-tbs5990.patch \
    file://0009-cx231xx-omit-encoder-state-from-tbs5990-core.patch \
    file://0010-cx231xx-omit-remote-control-state-from-tbs5990-core.patch \
    file://0011-cx231xx-support-legacy-4.x-media-APIs.patch \
    file://0012-cx231xx-isolate-TBS5990-from-legacy-analog-paths.patch \
    file://0013-media-support-legacy-DVB-callback-signatures.patch \
    file://0014-dvb-usb-route-TBS-remote-events-through-compatibilit.patch \
    file://0015-media-support-TBS-frontends-on-kernels-without-regma.patch \
    file://0016-media-complete-legacy-m88rs6060-regmap-shim.patch \
    file://0017-cx231xx-omit-analog-video-buffer-for-TBS5990.patch \
    file://0018-cx231xx-complete-DVB-only-TBS5990-core-module.patch \
    file://0019-media-support-transitional-regmap-and-si2157-APIs.patch \
    file://0020-dvb-usb-guard-si2157-if_port-on-legacy-kernels.patch \
    file://0021-av201x-support-legacy-tuner-release-callback.patch \
    file://0022-tbs5881-guard-si2168-ts-clock-inversion.patch \
"
SRCREV = "2ce787de9f6e81b4294692a45de15596ae2e1322"

PV = "1.0+git"

ALLOW_EMPTY:${PN} = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install[noexec] = "1"

SYSROOT_PREPROCESS_FUNCS += "tbs_stage_driver_sources"

tbs_stage_driver_sources() {
	install -d ${SYSROOT_DESTDIR}${datadir}/tbs-media-driver-source/drivers/media
	cp -R --no-preserve=ownership \
		${S}/drivers/media/dvb-frontends \
		${S}/drivers/media/tuners \
		${S}/drivers/media/usb \
		${SYSROOT_DESTDIR}${datadir}/tbs-media-driver-source/drivers/media/
}
