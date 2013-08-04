MODULE = "OpenAirPlay"
DESCRIPTION = "AirPlay and AirTunes server for enigma2"
RDEPENDS_${PN} = "python-twisted-core python-twisted-web python-m2crypto \
                  python-biplist python-netclient python-avahi python-dbus \
                  python-core python-subprocess python-io python-xmlrpc \
                  hairtunes"

inherit gitpkgv
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0.2"

require openplugins.inc

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/${MODULE}"
do_install() {
	install -d ${D}${PLUGINPATH}
	cp -rp ${S}/plugin/* ${D}${PLUGINPATH}
}

FILES_${PN} = "${PLUGINPATH}"

require assume-gplv2.inc
