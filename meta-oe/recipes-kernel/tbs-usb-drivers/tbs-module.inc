HOMEPAGE = "https://github.com/tbsdtv/linux_media"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit module

# Keep packages generated from these external modules distinct from modules
# emitted by the receiver kernel recipe.  This avoids package-feed and sstate
# collisions when a kernel also enables a module with the same basename.
KERNEL_MODULE_PACKAGE_PREFIX = "tbs-"

DEPENDS += "virtual/kernel tbs-media-driver-source ${TBS_MODULE_DEPENDS}"

SRC_URI = " \
    file://Makefile \
    file://tbs-kernel-compat.h \
"

PV = "1.0+git"
S = "${UNPACKDIR}"
B = "${WORKDIR}/build"
TBS_SOURCE_ROOT = "${STAGING_DATADIR}/tbs-media-driver-source"

TBS_SOURCE_FILES ?= ""
TBS_KBUILD_MODULES ?= ""
TBS_COMPOSITE_MODULE ?= ""
TBS_COMPOSITE_OBJECTS ?= ""
TBS_COMPOSITE_MODULE_2 ?= ""
TBS_COMPOSITE_OBJECTS_2 ?= ""
TBS_INSTALL_MODULES ?= ""
TBS_CCFLAGS ?= ""
TBS_DVB_ENUM_COMPAT ?= ""
# Some vendor kernels (notably Dreambox 4.9) enable -Werror globally.  Keep
# warnings in the external vendor sources visible without turning them into
# hard build failures for every TBS module.
TBS_CCFLAGS:append = " -Wno-error"
TBS_MODULE_DEPENDS ?= ""
TBS_KBUILD_EXTRA_SYMBOLS ?= ""

do_configure[depends] += "virtual/kernel:do_shared_workdir"

EXTRA_OEMAKE += " \
    KERNEL_SRC=${STAGING_KERNEL_DIR} \
    TBS_KBUILD_MODULES='${TBS_KBUILD_MODULES}' \
    TBS_COMPOSITE_MODULE='${TBS_COMPOSITE_MODULE}' \
    TBS_COMPOSITE_OBJECTS='${TBS_COMPOSITE_OBJECTS}' \
    TBS_COMPOSITE_MODULE_2='${TBS_COMPOSITE_MODULE_2}' \
    TBS_COMPOSITE_OBJECTS_2='${TBS_COMPOSITE_OBJECTS_2}' \
    TBS_CCFLAGS='${TBS_CCFLAGS}' \
    TBS_KBUILD_EXTRA_SYMBOLS='${TBS_KBUILD_EXTRA_SYMBOLS}' \
"
MAKE_TARGETS = "modules"

# External replacements may have the same module basename as modules offered
# by the receiver kernel.  Keep the TBS package names unique and only replace
# an installed in-kernel package when the user explicitly installs the TBS
# model package.  Do not advertise these modules as general providers: that
# would make unrelated driver packages select the TBS implementation.
TBS_KERNEL_MODULE_PACKAGES = "${@' '.join('kernel-module-%s' % module.replace('_', '-') for module in d.getVar('TBS_INSTALL_MODULES').split())}"
TBS_KERNEL_MODULE_PACKAGES_VERSIONED = "${@' '.join('kernel-module-%s-%s' % (module.replace('_', '-'), d.getVar('KERNEL_VERSION')) for module in d.getVar('TBS_INSTALL_MODULES').split())}"
RCONFLICTS:${PN} += "${TBS_KERNEL_MODULE_PACKAGES} ${TBS_KERNEL_MODULE_PACKAGES_VERSIONED}"
RREPLACES:${PN} += "${TBS_KERNEL_MODULE_PACKAGES} ${TBS_KERNEL_MODULE_PACKAGES_VERSIONED}"

do_configure() {
	install -d ${B}
	install -m 0644 ${UNPACKDIR}/Makefile ${B}/Makefile
	install -m 0644 ${UNPACKDIR}/tbs-kernel-compat.h ${B}/
	: > ${B}/tbs-kernel-compat.mk
	# Older receiver kernels keep the in-kernel DVB API headers below
	# drivers/media/dvb-core instead of exporting them through include/media.
	# Stage the headers from the selected machine kernel so both include styles
	# used by the TBS sources resolve against that exact kernel version.
	install -d ${B}/media
	if [ -f ${STAGING_KERNEL_DIR}/drivers/media/dvb-core/dvb_frontend.h ]; then
		for header in ${STAGING_KERNEL_DIR}/drivers/media/dvb-core/*.h; do
			[ -f "$header" ] || continue
			install -m 0644 $header ${B}/media/
		done
		dvb_frontend_header=${STAGING_KERNEL_DIR}/drivers/media/dvb-core/dvb_frontend.h
	elif [ -f ${STAGING_KERNEL_DIR}/drivers/media/dvb/dvb-core/dvb_frontend.h ]; then
		for header in ${STAGING_KERNEL_DIR}/drivers/media/dvb/dvb-core/*.h; do
			[ -f "$header" ] || continue
			install -m 0644 $header ${B}/media/
		done
		dvb_frontend_header=${STAGING_KERNEL_DIR}/drivers/media/dvb/dvb-core/dvb_frontend.h
	else
		dvb_frontend_header=${STAGING_KERNEL_DIR}/include/media/dvb_frontend.h
	fi
	if [ -f ${STAGING_KERNEL_DIR}/include/uapi/linux/dvb/frontend.h ]; then
		dvb_uapi_header=${STAGING_KERNEL_DIR}/include/uapi/linux/dvb/frontend.h
	else
		dvb_uapi_header=${STAGING_KERNEL_DIR}/include/linux/dvb/frontend.h
	fi
	# Vendor kernels frequently backport only part of the newer DVB-S2X enum.
	# Add aliases one by one so an existing backport is never replaced while
	# its enum is being declared.
	for compat in ${TBS_DVB_ENUM_COMPAT}; do
		symbol=${compat%%=*}
		if ! grep -Eq "[[:space:]]${symbol}([[:space:]=,])" \
			$dvb_uapi_header; then
			printf 'ccflags-y += -D%s\n' "$compat" \
				>> ${B}/tbs-kernel-compat.mk
		fi
	done
	if ! grep -q "frequency_min_hz" \
		$dvb_frontend_header; then
		printf '%s\n' 'ccflags-y += -DTBS_LEGACY_DVB_FREQUENCY_API=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if ! grep -q "scrambling_sequence_index" \
		$dvb_frontend_header; then
		printf '%s\n' 'ccflags-y += -DTBS_NO_SCRAMBLING_SEQUENCE_INDEX=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if ! grep -q "i2c_new_client_device" \
		${STAGING_KERNEL_DIR}/include/linux/i2c.h; then
		printf '%s\n' 'ccflags-y += -DTBS_LEGACY_I2C_NEW_DEVICE=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if ! grep -q "i2c_client_has_driver" \
		${STAGING_KERNEL_DIR}/include/linux/i2c.h; then
		printf '%s\n' 'ccflags-y += -DTBS_LEGACY_I2C_CLIENT_HAS_DRIVER=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if ! grep -q "RC_PROTO_BIT_NEC" \
		${STAGING_KERNEL_DIR}/include/media/rc-map.h; then
		printf '%s\n' \
			'ccflags-y += -DTBS_LEGACY_RC_PROTOCOL_NAMES=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if ! grep -q 'void rc_keydown.*protocol' \
		${STAGING_KERNEL_DIR}/include/media/rc-core.h; then
		printf '%s\n' \
			'ccflags-y += -DTBS_LEGACY_RC_KEYDOWN=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if ! grep -q "strscpy" \
		${STAGING_KERNEL_DIR}/include/linux/string.h; then
		printf '%s\n' 'ccflags-y += -DTBS_LEGACY_STRSCPY=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if [ ! -e ${STAGING_KERNEL_DIR}/include/media/drv-intf/cx25840.h ]; then
		printf '%s\n' 'ccflags-y += -DTBS_LEGACY_CX25840_HEADER=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	# Some vendor kernels contain a partial i2c-mux backport: the core
	# structure and allocator are present, but the matching add/remove API is
	# not.  Treat those kernels as legacy too instead of selecting functions
	# which their headers and kernel image do not provide.
	if ! grep -q "struct i2c_mux_core" \
		${STAGING_KERNEL_DIR}/include/linux/i2c-mux.h || \
		! grep -q "i2c_mux_add_adapter" \
		${STAGING_KERNEL_DIR}/include/linux/i2c-mux.h || \
		! grep -q "i2c_mux_del_adapters" \
		${STAGING_KERNEL_DIR}/include/linux/i2c-mux.h; then
		printf '%s\n' 'ccflags-y += -DTBS_LEGACY_I2C_MUX_API=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if ! grep -q "media_device_usb_init" \
		${STAGING_KERNEL_DIR}/include/media/media-device.h || \
		! grep -Rq "v4l2_mc_create_media_graph" \
		${STAGING_KERNEL_DIR}/include/media; then
		printf '%s\n' 'ccflags-y += -DTBS_LEGACY_MEDIA_CONTROLLER_API=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if ! grep -Rq "#define IS_REACHABLE" \
		${STAGING_KERNEL_DIR}/include; then
		printf '%s\n' \
			'ccflags-y += -std=gnu89 -DTBS_LEGACY_IS_REACHABLE=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if grep -q 'int (\*release)(struct dvb_frontend \*fe);' \
		$dvb_frontend_header; then
		printf '%s\n' \
			'ccflags-y += -DTBS_LEGACY_TUNER_RELEASE_CALLBACK=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if grep -q 'int (\*get_frontend)(struct dvb_frontend \*fe);' \
		$dvb_frontend_header; then
		printf '%s\n' \
			'ccflags-y += -DTBS_LEGACY_GET_FRONTEND_CALLBACK=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if [ ! -f ${STAGING_KERNEL_BUILDDIR}/.config ] || \
		! grep -q '^CONFIG_REGMAP=y' ${STAGING_KERNEL_BUILDDIR}/.config || \
		! grep -q '^CONFIG_REGMAP_I2C=y' ${STAGING_KERNEL_BUILDDIR}/.config; then
		printf '%s\n' \
			'ccflags-y += -DTBS_LEGACY_REGMAP=1' \
			>> ${B}/tbs-kernel-compat.mk
	elif ! grep -q 'struct reg_sequence' \
		${STAGING_KERNEL_DIR}/include/linux/regmap.h; then
		printf '%s\n' \
			'ccflags-y += -DTBS_LEGACY_REG_SEQUENCE=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if [ -f ${STAGING_KERNEL_DIR}/drivers/media/dvb-frontends/si2168.h ] && \
		! grep -q 'ts_clock_gapped' \
		${STAGING_KERNEL_DIR}/drivers/media/dvb-frontends/si2168.h; then
		printf '%s\n' \
			'ccflags-y += -DTBS_NO_SI2168_TS_CLOCK_GAPPED=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	if [ -f ${STAGING_KERNEL_DIR}/drivers/media/tuners/si2157.h ] && \
		! grep -q 'if_port' \
		${STAGING_KERNEL_DIR}/drivers/media/tuners/si2157.h; then
		printf '%s\n' \
			'ccflags-y += -DTBS_NO_SI2157_IF_PORT=1' \
			>> ${B}/tbs-kernel-compat.mk
	fi
	for source in ${TBS_SOURCE_FILES}; do
		install -m 0644 ${TBS_SOURCE_ROOT}/$source ${B}/
	done
}

do_install() {
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/tbs
	for module in ${TBS_INSTALL_MODULES}; do
		test -f ${B}/$module.ko
		install -m 0644 ${B}/$module.ko \
			${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/tbs/
	done
}

SYSROOT_PREPROCESS_FUNCS += "tbs_stage_module_symvers"

tbs_stage_module_symvers() {
	if [ -s ${B}/Module.symvers ]; then
		install -d ${SYSROOT_DESTDIR}${datadir}/tbs-module-symvers/${PN}
		install -m 0644 ${B}/Module.symvers \
			${SYSROOT_DESTDIR}${datadir}/tbs-module-symvers/${PN}/Module.symvers
	fi
}
