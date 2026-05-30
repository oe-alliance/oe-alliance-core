NAME = "Tailscale"
SUMMARY = "Tailscale is a zero config VPN for building secure networks"
MAINTAINER = "Spaze Team <xx@xxx.xx>"
HOMEPAGE = "https://openspa.info"
SECTION = "extra"
PRIORITY = "optional"
RDEPENDS:${PN} = "python3-requests tailscale"

require assume-gplv2.inc
require conf/python/python3-compileall.inc
require spaopenplugins.inc

PV = "1.1+git"
FOLDER = "tailscale"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${NAME}"

S = "${UNPACKDIR}/${P}"

do_install() {
    install -d ${D}${PLUGINPATH}

    cp -r ${S}/${FOLDER}/plugin/* ${D}${PLUGINPATH}
}

do_install:append() {
    find ${D}${PLUGINPATH} -name "*.py" -delete
}

pkg_postinst:${PN}() {
#!/bin/sh

if [ ! -d /etc/keys/ ]; then
    mkdir /etc/keys
fi

if [ ! -f /etc/keys/tailscale.key ]; then
    echo " " >> /etc/keys/tailscale.key
fi

if [ ! -f /etc/keys/tailscale_api.key ]; then
    echo " " >> /etc/keys/tailscale_api.key
fi
exit 0
}

FILES:${PN} = "${PLUGINPATH}"
