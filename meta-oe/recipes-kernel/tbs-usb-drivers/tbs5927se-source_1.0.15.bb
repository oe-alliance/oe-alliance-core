SUMMARY = "TBS5927SE driver sources for receiver kernels"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

require tbs5927se-vendor.inc

SRC_URI += "file://0001-tbs5927se-support-receiver-kernel-APIs.patch"

# The vendor archive uses DOS line endings; keep the patches normal text.
do_patch[prefuncs] += "tbs5927se_normalize_sources"

tbs5927se_normalize_sources() {
	sed -i 's/\r$//' ${S}/frontends/avl62x1.c ${S}/tuners/rda5815.c ${S}/dvb-usb/tbs5927se.c
}

ALLOW_EMPTY:${PN} = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install[noexec] = "1"

# Stage only the new bridge/frontend/tuner; keep the receiver DVB core intact.
SYSROOT_PREPROCESS_FUNCS += "tbs5927se_stage_sources"

tbs5927se_stage_sources() {
	for source in \
		dvb-usb/tbs5927se.c dvb-usb/tbs5927se.h \
		frontends/avl62x1.c frontends/avl62x1.h \
		frontends/avl62x1_priv.h frontends/avl62x1_regs.h \
		tuners/rda5815.c tuners/rda5815.h; do
		install -Dm 0644 ${S}/$source \
			${SYSROOT_DESTDIR}${datadir}/tbs5927se-source/$source
	done
}
