require directfb-${MACHINE}_1.7.7.inc

GFX_FILE = "directfb_1.7-7_gfxdriver_${MACHINE}_${GFXPR}.tar.gz"

PR .= ".2"

GFX_URI ?= "http://archive.vuplus.com/download/build_support/vuplus/${GFX_FILE};name=gfxdriver"

SRC_URI += " \
	file://vuplus-conf.patch \
	file://vuplus-prevent-VT.patch \
	file://disable_linux_input_patch.diff \
	${GFX_URI} \
"

EXTRA_OECONF += "\
	--with-inputdrivers=linuxinput \
	--disable-devmem \
	--disable-video4linux \
	--without-tools \
"

PRIVATE_LIBS_${PN} = "\
	${@bb.utils.contains("MACHINE_FEATURES", "dvbproxy", "libinit.so", "", d)} \
"

do_install_append() {
	install -d ${D}/etc
	install -d ${D}/usr/lib/directfb-${RV}/systems
	install -d ${D}/usr/lib/directfb-${RV}/gfxdrivers

	install -m 0644 ${WORKDIR}/directfb-vuplus/etc/directfbrc ${D}/etc/directfbrc
	cp -avr ${WORKDIR}/directfb-vuplus/usr/lib/directfb-${RV}/systems/* ${D}/usr/lib/directfb-${RV}/systems/
	rm -f ${D}/usr/lib/directfb-${RV}/systems/libdirectfb_dummy.so
	rm -f ${D}/usr/lib/directfb-${RV}/systems/libdirectfb_fbdev.so
	install -m 0755 ${WORKDIR}/directfb-vuplus/usr/lib/directfb-${RV}/gfxdrivers/* ${D}/usr/lib/directfb-${RV}/gfxdrivers/

	cp -avr ${WORKDIR}/directfb-vuplus/usr/lib/libinit* ${D}/usr/lib/
}

do_package_qa() {
}

FILES_${PN} += "\
	/etc/directfbrc \
	/usr/lib/libinit* \
	/usr/lib/directfb-${RV}/systems/libdirectfb_dvb_proxy_sys* \
	/usr/lib/directfb-${RV}/gfxdrivers/* \
"

FILES_${PN}-dbg += "\
	/usr/lib/.debug/libinit* \
	/usr/lib/directfb-${RV}/systems/.debug/* \
	/usr/lib/directfb-${RV}/gfxdrivers/.debug/* \
"

BBCLASSEXTEND = "native"
PACKAGE_ARCH := "${MACHINE_ARCH}"