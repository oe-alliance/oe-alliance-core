SUMMARY = "Ready-to-use SRT / WebRTC / RTSP / RTMP / LL-HLS media server and media proxy that allows to read, publish, proxy, record and playback video and audio streams."
DESCRIPTION = "${SUMMARY}"
HOMEPAGE = "https://github.com/bluenviron/mediamtx"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=77fd2623bd5398430be5ce60489c2e81"

inherit gittag go-mod update-rc.d systemd upx-compress

VERSION := "${PV}"
SRCREV = "b66efd66da92ce475f3bf2a229efa56724f3fa64"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/bluenviron/mediamtx.git;protocol=https;branch=main;destsuffix=${GO_SRCURI_DESTSUFFIX} \
		file://add_support_for_http_over_unix_sockets.patch;patchdir=src/${GO_IMPORT} \
		file://mediamtx.initd \
		file://mediamtx.yml \
"

do_configure:append () {
	sed -i "s/v0.0.0/v${VERSION}/g" ${B}/src/${GO_IMPORT}/internal/core/versiongetter/main.go
}

do_compile:prepend () {
	go generate ./...
	chmod -R +w "$GOMODCACHE"
}

FILES:${PN} += "${systemd_unitdir} ${sysconfdir}"

do_install() {
	install -d ${D}${bindir}
	if [ -d ${B}/bin/linux_mipsle ]; then
		install -m 755 ${B}/bin/linux_mipsle/mediamtx ${D}${bindir}
	elif [ -d ${B}/bin/linux_arm64 ]; then
		install -m 755 ${B}/bin/linux_arm64/mediamtx ${D}${bindir}
	else
		install -m 755 ${B}/bin/linux_${TARGET_ARCH}/mediamtx ${D}${bindir}
	fi

	install -d ${D}${sysconfdir}/${PN}
	install -m 755 ${UNPACKDIR}/mediamtx.yml ${D}${sysconfdir}/${PN}

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${UNPACKDIR}/mediamtx.initd ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}

INITSCRIPT_NAME = "${PN}-daemon"
INITSCRIPT_PARAMS = "defaults 60 "
