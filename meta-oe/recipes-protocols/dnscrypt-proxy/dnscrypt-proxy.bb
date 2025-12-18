SUMMARY = "Flexible DNS proxy with support for DNSCrypt v2, DoH, Anonymized DNSCrypt and ODoH"
HOMEPAGE = "https://github.com/DNSCrypt/dnscrypt-proxy"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/${GO_SRCURI_DESTSUFFIX}/LICENSE;md5=ab28537c54beaee1dff55cf590239a91"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "\
    git://${GO_IMPORT}.git;protocol=https;branch=master;destsuffix=${GO_SRCURI_DESTSUFFIX}  \
    file://dnscrypt-proxy.init \
    file://dnscrypt-proxy.toml \
"

GO_IMPORT = "github.com/DNSCrypt/dnscrypt-proxy"

inherit gittag go-mod update-rc.d upx-compress

export CGO_ENABLED = "0"

INITSCRIPT_NAME = "dnscrypt-proxy"
INITSCRIPT_PARAMS = "defaults 20"

RDEPENDS:${PN} += "ca-certificates"

do_compile() {
    cd ${UNPACKDIR}/${GO_SRCURI_DESTSUFFIX}
    ${GO} build -trimpath -ldflags="-s -w" -mod=vendor -o ${B}/dnscrypt-proxy ./dnscrypt-proxy
}


do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/dnscrypt-proxy ${D}${bindir}/dnscrypt-proxy
    install -d ${D}${sysconfdir}/dnscrypt-proxy
    install -m 0644 ${UNPACKDIR}/dnscrypt-proxy.toml ${D}${sysconfdir}/dnscrypt-proxy/dnscrypt-proxy.toml
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/dnscrypt-proxy.init ${D}${sysconfdir}/init.d/dnscrypt-proxy

}

CONFFILES:${PN} += "${sysconfdir}/dnscrypt-proxy/dnscrypt-proxy.toml"

FILES:${PN} += "\
    ${sysconfdir}/dnscrypt-proxy \
    ${sysconfdir}/init.d/dnscrypt-proxy \
"
