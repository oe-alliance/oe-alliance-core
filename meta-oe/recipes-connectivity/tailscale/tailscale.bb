SUMMARY = "Tailscale client and daemon for Linux"
HOMEPAGE = "github.com/tailscale/tailscale"
SECTION = "net"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${GO_SRCURI_DESTSUFFIX}/LICENSE;md5=a672713a9eb730050e491c92edf7984d"

RRECOMMENDS:${PN} = "kernel-module-tun enigma2-plugin-drivers-iptables"

inherit gitpkgv

SRCREV = "ede81e2669bc01d60f52c84eea1d404215b13e16"
PV = "1.64.2+git"
PKGV = "1.64.2+git${GITPKGV}"

SRC_URI = "git://github.com/tailscale/tailscale.git;protocol=https;branch=release-branch/1.64 \
        file://tailscaled.initd \
"

inherit go-mod update-rc.d systemd upx-compress

GO_IMPORT = "tailscale.com"
GO_WORKDIR = "${GO_IMPORT}"
GO_INSTALL = "${GO_IMPORT}/cmd/tailscale ${GO_IMPORT}/cmd/tailscaled"
export GOPROXY = "https://proxy.golang.org,direct"

# Fixes duplicated definition of symbols errors by linking for arm arch
GO_DYNLINK:arm = ""
GO_DYNLINK:aarch64 = ""

FILES:${PN} += "${systemd_unitdir} ${sysconfdir}"

# Allow downloads during compile
do_compile[network] = "1"

do_install() {
	install -d ${D}${bindir}
	install -d ${D}${sbindir}
	if [ -d ${B}/bin/linux_mipsle ]; then
		install ${B}/bin/linux_mipsle/tailscale ${D}${bindir}/tailscale
		install ${B}/bin/linux_mipsle/tailscaled ${D}${sbindir}/tailscaled
	elif [ -d ${B}/bin/linux_arm64 ]; then
		install ${B}/bin/linux_arm64/tailscale ${D}${bindir}/tailscale
		install ${B}/bin/linux_arm64/tailscaled ${D}${sbindir}/tailscaled
	else
		install ${B}/bin/linux_${TARGET_ARCH}/tailscale ${D}${bindir}/tailscale
		install ${B}/bin/linux_${TARGET_ARCH}/tailscaled ${D}${sbindir}/tailscaled
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/tailscaled.initd ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/default/
		install -m 0644
		${WORKDIR}/build/src/${GO_IMPORT}/cmd/tailscaled/tailscaled.defaults
		${D}${sysconfdir}/default/tailscaled
		install -d ${D}${systemd_unitdir}/system
		install -m 0644
		${WORKDIR}/build/src/${GO_IMPORT}/cmd/tailscaled/tailscaled.service
		${D}${systemd_unitdir}/system/tailscaled.service
		install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
		ln -s ${systemd_unitdir}/system/tailscaled.service
		${D}${sysconfdir}/systemd/system/multi-user.target.wants/tailscaled.service
	fi
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "tailscaled.service"
SYSTEMD_AUTO_ENABLE = "enable"

INITSCRIPT_NAME = "tailscale-daemon"
INITSCRIPT_PARAMS = "defaults 60 "
